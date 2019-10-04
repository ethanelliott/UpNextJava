package ca.ethanelliott.upnext.server.upnext;

import ca.ethanelliott.upnext.server.database.Database;
import ca.ethanelliott.upnext.server.requests.HTTP_METHODS;
import ca.ethanelliott.upnext.server.socket.Message;
import ca.ethanelliott.upnext.server.socket.Messenger;
import ca.ethanelliott.upnext.server.spotify.SpotifyApi;
import ca.ethanelliott.upnext.server.spotify.WebApiRequestBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class UpNext extends Thread {
    private static UpNext instance = null;

    public static UpNext getInstance() {
        if (instance == null) {
            System.out.println("Created New UpNext");
            instance = new UpNext();
        }
        return instance;
    }

    private Database database;
    private Messenger messenger;
    private SpotifyApi spotifyApi;
    private Map<String, Party> partyMap = new HashMap<>();
    private Map<String, Function<Message, Object>> eventLookup;

    private UpNext() {
        this.eventLookup = new HashMap<>();
        this.database = Database.getInstance();
        this.messenger = Messenger.getInstance();
        this.spotifyApi = new SpotifyApi();
        this.start();
    }

    private void processMessage(Message message) {
        String eventIdentifier = message.getData().getEventIdentifier();
        if (this.eventLookup.containsKey(eventIdentifier)) {
            this.eventLookup.get(eventIdentifier).apply(message);
        } else {
            throw new IllegalStateException("Unknown event identifier: " + eventIdentifier);
        }
    }

    private void registerEvents() {
        System.out.println("registered");
        this.on("create-party", (Message message) -> {
            this.createParty(message);
            return null;
        });
        this.on("event-loop", (Message message) -> {
            this.eventLoop(message);
            return null;
        });
        this.on("validate-party-code", (Message message) -> {
            this.validatePartyCode(message);
            return null;
        });
        this.on("toggle-playback", (Message message) -> {
            this.togglePlayback(message);
            return null;
        });
        this.on("skip-song", (Message message) -> {
            this.skipSong(message);
            return null;
        });
        this.on("get-queue", (Message message) -> {
            this.giveQueue(message);
            return null;
        });
        this.on("search", (Message message) -> {
            this.search(message);
            return null;
        });
        this.on("add-song", (Message message) -> {
            this.addSong(message);
            return null;
        });
    }

    private void addSong(Message message) {
        Map<String, Object> data = (Map<String, Object>) message.getData().getData();
        Party party = this.partyMap.get(data.get("party-id"));
        String songID = (String) data.get("song-id");

        HashMap<String, Object> pp = WebApiRequestBuilder
                .make(party.getToken())
                .withMethod(HTTP_METHODS.GET)
                .withPath(String.format("/v1/tracks/%s", songID))
                .build()
                .execute();
        System.out.println(pp);
        PlaylistEntry playlistEntry = new PlaylistEntry();
        playlistEntry.setId(songID);
        party.getPlaylist().add(playlistEntry);
        System.out.println(party.getPlaylist());
    }

    private void search(Message message) {
        Map<String, Object> data = (Map<String, Object>) message.getData().getData();
        Party party = this.partyMap.get(data.get("party-id"));
        String searchTerm = (String) data.get("search-terms");
        Map<String, String> qp = new HashMap<>();
        qp.put("q", searchTerm);
        qp.put("type", "track");
        qp.put("market", "CA");
        qp.put("limit", "20");
        qp.put("offset", "0");
        HashMap<String, Object> pp = WebApiRequestBuilder
                .make(party.getToken())
                .withMethod(HTTP_METHODS.GET)
                .withPath("/v1/search")
                .withQueryParameters(qp)
                .build()
                .execute();
        this.messenger.postToQueueByAddress(
                message.getSourceAddress(),
                new Message(
                        "*",
                        message.getSourceAddress(),
                        "search-results",
                        pp
                )
        );
    }

    private void giveQueue(Message message) {
        String partyID = (String) message.getData().getData();
        Party party = this.partyMap.get(partyID);
        this.messenger.postToQueueByAddress(
                message.getSourceAddress(),
                new Message(
                        "*",
                        message.getSourceAddress(),
                        "give-queue",
                        party
                )
        );
    }

    private void skipSong(Message message) {
        String data = (String) message.getData().getData();
        Party party = this.partyMap.get(data);
        HashMap<String, Object> pp = WebApiRequestBuilder
                .make(party.getToken())
                .withMethod(HTTP_METHODS.POST)
                .withPath("/v1/me/player/next")
                .build()
                .execute();
    }

    private void togglePlayback(Message message) {
        Map<String, Object> data = (HashMap<String, Object>) message.getData().getData();
        Party party = this.partyMap.get(data.get("party-id"));
        boolean isPlaying = (boolean) data.get("is-playing");
        if (isPlaying) {
            HashMap<String, Object> pp = WebApiRequestBuilder
                    .make(party.getToken())
                    .withMethod(HTTP_METHODS.PUT)
                    .withPath("/v1/me/player/pause")
                    .build()
                    .execute();
            System.out.println(pp);
        } else {
            HashMap<String, Object> pp = WebApiRequestBuilder
                    .make(party.getToken())
                    .withMethod(HTTP_METHODS.PUT)
                    .withPath("/v1/me/player/play")
                    .build()
                    .execute();
            System.out.println(pp);
        }

    }

    private void validatePartyCode(Message message) {
        String partyCode = (String) message.getData().getData();
        boolean isFound = false;
        String partyID = "";
        for (Map.Entry<String, Party> entry : this.partyMap.entrySet()) {
            Party party = entry.getValue();
            if (party.getCode().toUpperCase().equals(partyCode.toUpperCase())) {
                isFound = true;
                partyID = party.getUuid();
                break;
            }
        }
        if (isFound) {
            this.messenger.postToQueueByAddress(message.getSourceAddress(), new Message("*", message.getSourceAddress(), "party-code-good", partyID));
        } else {
            this.messenger.postToQueueByAddress(message.getSourceAddress(), new Message("*", message.getSourceAddress(), "party-code-bad", null));
        }
    }

    @Override
    public void run() {
        this.registerEvents();
        while (true) {
            Message m = this.messenger.getMessage();
            if (m != null) {
                this.processMessage(m);
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Database getDatabase() {
        return database;
    }

    private void createParty(Message message) {
        System.out.println("NEW PARTY CREATED!");
        Map<String, Object> data = (Map<String, Object>) message.getData().getData();
        Map<String, Object> userData = spotifyApi.users.getCurrentUsersProfile((String) ((Map<String, Object>) data.get("tokens")).get("access_token"));
        Party party = PartyBuilder.build(
                (String) data.get("name"),
                (String) data.get("password"),
                (String) ((Map<String, Object>) data.get("tokens")).get("access_token"),
                (String) ((Map<String, Object>) data.get("tokens")).get("refresh_token"),
                (double) ((Map<String, Object>) data.get("tokens")).get("expires_in"),
                (String) userData.get("id")
        );
        this.partyMap.put(party.getUuid(), party);
        System.out.println(spotifyApi.tracks.getTrack(party.getToken(), "12mGwph2YzDIlChtq3EdXP"));
        this.messenger.postToQueueByAddress(message.getSourceAddress(), new Message("*", message.getSourceAddress(), "party-created", party));
    }

    private void eventLoop(Message message) {
        Party party = this.partyMap.get((String) message.getData().getData());
        HashMap<String, Object> playerState = WebApiRequestBuilder
                .make(party.getToken())
                .withMethod(HTTP_METHODS.GET)
                .withPath("/v1/me/player")
                .build()
                .execute();
        HashMap<String, Object> response = new HashMap<>();
        response.put("party", party);
        response.put("player", playerState);
        this.messenger.postToQueueByAddress(message.getSourceAddress(), new Message("*", message.getSourceAddress(), "event-loop-response", response));
    }

    public void on(String eventIdentifier, Function<Message, Object> eventHandler) {
        this.eventLookup.put(eventIdentifier, eventHandler);
    }
}