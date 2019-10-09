package ca.ethanelliott.upnext.server.upnext;

import ca.ethanelliott.upnext.server.database.Database;
import ca.ethanelliott.upnext.server.socket.Message;
import ca.ethanelliott.upnext.server.socket.Messenger;
import ca.ethanelliott.upnext.server.spotify.SpotifyApi;
import ca.ethanelliott.upnext.server.spotify.types.*;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UpNext extends Thread {
    private static UpNext instance = null;

    public static UpNext getInstance() {
        if (instance == null) {
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
        this.spotifyApi = SpotifyApi.getInstance();
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
            this.giveQueue((String) message.getData().getData(), message.getSourceAddress());
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

        this.on("upvote-song", (Message message) -> {
            this.upvoteSong(message);
            return null;
        });
        this.on("downvote-song", (Message message) -> {
            this.downvoteSong(message);
            return null;
        });
    }

    private void downvoteSong(Message message) {
        Map<String, Object> data = (Map<String, Object>) message.getData().getData();
        Party party = this.partyMap.get(data.get("party-id"));
        String songID = (String) data.get("song-id");
        List<PlaylistEntry> playlist = party.getPlaylist();
        for (int i = 0; i < playlist.size(); i++) {
            int vote = playlist.get(i).getVotes();
            if (playlist.get(i).getId().equals(songID)) {
                party.getPlaylist().get(i).setVotes(vote - 1);
            }
        }
        this.giveQueue(party.getUuid(), message.getSourceAddress());
    }

    private void upvoteSong(Message message) {
        Map<String, Object> data = (Map<String, Object>) message.getData().getData();
        Party party = this.partyMap.get(data.get("party-id"));
        String songID = (String) data.get("song-id");
        List<PlaylistEntry> playlist = party.getPlaylist();
        for (int i = 0; i < playlist.size(); i++) {
            int vote = playlist.get(i).getVotes();
            if (playlist.get(i).getId().equals(songID)) {
                party.getPlaylist().get(i).setVotes(vote + 1);
            }
        }
        this.giveQueue(party.getUuid(), message.getSourceAddress());
    }

    private void addSong(Message message) {
        Map<String, Object> data = (Map<String, Object>) message.getData().getData();
        Party party = this.partyMap.get(data.get("party-id"));
        String songID = (String) data.get("song-id");
        TrackObject trackObject = spotifyApi.tracks.getTrack(party.getToken(), songID);
        String name = trackObject.name;
        PlaylistEntry playlistEntry = new PlaylistEntry();
        playlistEntry.setId(songID);
        playlistEntry.setName(name);
        playlistEntry.setArtwork(trackObject.album.images.stream().filter(e -> e.width < 100).collect(Collectors.toList()).get(0).url);
        playlistEntry.setArtist(trackObject.artists.stream().map(e -> e.name).collect(Collectors.joining(",")));
        party.getPlaylist().add(playlistEntry);
        this.giveQueue(party.getUuid(), message.getSourceAddress());
    }

    private void search(Message message) {
        Map<String, Object> data = (Map<String, Object>) message.getData().getData();
        Party party = this.partyMap.get(data.get("party-id"));
        String searchTerm = (String) data.get("search-terms");
        SearchResultObject tracks = spotifyApi.search.searchTracks(party.getToken(), searchTerm);
        this.messenger.postToQueueByAddress(
                message.getSourceAddress(),
                new Message(
                        "*",
                        message.getSourceAddress(),
                        "search-results",
                        tracks
                )
        );
    }

    private void giveQueue(String partyID, String sourceAddress) {
        Party party = this.partyMap.get(partyID);
        String playlist = new Gson().toJson(party.getPlaylist());
        this.messenger.postToQueueByAddress(
                sourceAddress,
                new Message(
                        "*",
                        sourceAddress,
                        "give-queue",
                        playlist
                )
        );
    }

    private void skipSong(Message message) {
        String data = (String) message.getData().getData();
        Party party = this.partyMap.get(data);
        spotifyApi.player.next(party.getToken());
    }

    private void togglePlayback(Message message) {
        Map<String, Object> data = (HashMap<String, Object>) message.getData().getData();
        Party party = this.partyMap.get(data.get("party-id"));
        boolean isPlaying = (boolean) data.get("is-playing");
        if (isPlaying) {
            spotifyApi.player.pause(party.getToken());
        } else {
            spotifyApi.player.play(party.getToken());
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
        Map<String, Object> data = (Map<String, Object>) message.getData().getData();
        AuthObject authObject = (AuthObject) data.get("tokens");
        PrivateUserObject userData = spotifyApi.users.getCurrentUsersProfile(authObject.access_token);
        Party party = PartyBuilder.build(
                (String) data.get("name"),
                (String) data.get("password"),
                authObject.access_token,
                authObject.refresh_token,
                authObject.expires_in,
                userData.id
        );
        this.partyMap.put(party.getUuid(), party);
        this.messenger.postToQueueByAddress(message.getSourceAddress(), new Message("*", message.getSourceAddress(), "party-created", party));
    }

    private void eventLoop(Message message) {
        Party party = this.partyMap.get((String) message.getData().getData());

        CurrentlyPlayingObject playerState = spotifyApi.player.getPlayingContext(party.getToken());
        HashMap<String, Object> response = new HashMap<>();
        response.put("party", party);
        response.put("player", playerState);
        this.messenger.postToQueueByAddress(message.getSourceAddress(), new Message("*", message.getSourceAddress(), "event-loop-response", response));
    }

    public void on(String eventIdentifier, Function<Message, Object> eventHandler) {
        this.eventLookup.put(eventIdentifier, eventHandler);
    }
}