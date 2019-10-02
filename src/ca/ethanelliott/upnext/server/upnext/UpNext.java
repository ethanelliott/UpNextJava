package ca.ethanelliott.upnext.server.upnext;

import ca.ethanelliott.upnext.server.database.Database;
import ca.ethanelliott.upnext.server.socket.Message;
import ca.ethanelliott.upnext.server.socket.Messenger;
import ca.ethanelliott.upnext.server.spotify.Credentials;
import ca.ethanelliott.upnext.server.spotify.CredentialsBuilder;
import ca.ethanelliott.upnext.server.spotify.SpotifyApi;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedTransferQueue;

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

    private UpNext() {
        database = Database.getInstance();
        messenger = Messenger.getInstance();
        Credentials c = CredentialsBuilder.builder()
                .setAccessToken("")
                .setClientID("")
                .setClientSecret("")
                .setRedirectURI("")
                .setRefreshToken("")
                .build();
        spotifyApi = new SpotifyApi(c);
        this.start();
    }

    private void processMessage(Message message) {
        switch (message.getData().getEventIdentifier()) {
            case "create-party":
                System.out.println("NEW PARTY CREATED!");
                Map<String, Object> data = new HashMap<>();
                data.put("code", "BATH");
                this.messenger.postToQueueByAddress(message.getSourceAddress(), new Message("*", message.getSourceAddress(), "party-created", data));
                break;
            case "event-loop":
                // PLEASE DO NOTHING
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
}