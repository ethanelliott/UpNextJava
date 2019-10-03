package ca.ethanelliott.upnext.server.upnext;

import ca.ethanelliott.upnext.server.database.Database;
import ca.ethanelliott.upnext.server.socket.Message;
import ca.ethanelliott.upnext.server.socket.Messenger;
import ca.ethanelliott.upnext.server.spotify.Credentials;
import ca.ethanelliott.upnext.server.spotify.CredentialsBuilder;
import ca.ethanelliott.upnext.server.spotify.SpotifyApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private UpNext() {
        database = Database.getInstance();
        messenger = Messenger.getInstance();
        Credentials credentials = CredentialsBuilder.builder()
                .setAccessToken("")
                .setClientID("")
                .setClientSecret("")
                .setRedirectURI("")
                .setRefreshToken("")
                .build();
        spotifyApi = new SpotifyApi(credentials);
        this.start();
    }

    private void processMessage(Message message) {
        switch (message.getData().getEventIdentifier()) {
            case "create-party":
                this.createParty(message);
                break;
            case "event-loop":
                // PLEASE DO NOTHING
                this.eventLoop(message);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + message.getData().getEventIdentifier());
        }
    }

    @Override
    public void run() {
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
        Party party = PartyBuilder.build(
                (String) data.get("name"),
                (String) data.get("password"),
                (String) ((Map<String, Object>) data.get("tokens")).get("access_token"),
                (String) ((Map<String, Object>) data.get("tokens")).get("refresh_token"),
                (double) ((Map<String, Object>) data.get("tokens")).get("expires_in"),
                "",
                ""
        );
        this.partyMap.put(party.getUuid(), party);
        this.messenger.postToQueueByAddress(message.getSourceAddress(), new Message("*", message.getSourceAddress(), "party-created", party));
    }

    private void eventLoop(Message message) {
        Party party = this.partyMap.get((String) message.getData().getData());
        this.messenger.postToQueueByAddress(message.getSourceAddress(), new Message("*", message.getSourceAddress(), "event-loop-response", party));
    }
}