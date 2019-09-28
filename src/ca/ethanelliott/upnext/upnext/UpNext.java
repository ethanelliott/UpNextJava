package ca.ethanelliott.upnext.upnext;

import ca.ethanelliott.upnext.database.Database;
import ca.ethanelliott.upnext.socket.Message;
import ca.ethanelliott.upnext.spotify.Credentials;
import ca.ethanelliott.upnext.spotify.CredentialsBuilder;
import ca.ethanelliott.upnext.spotify.SpotifyApi;

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
    private LinkedTransferQueue<Message> messageQueue;
    private SpotifyApi spotifyApi;

    private UpNext() {
        database = Database.getInstance();
        messageQueue = new LinkedTransferQueue<>();
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

    @Override
    public void run() {
        while (true) {
            System.out.println("LOOP" + this.messageQueue.size());
            Message m = null;
            try {
                if (this.messageQueue.size() != 0) {
                    m = this.messageQueue.take();
                    System.out.println(m);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
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

    public LinkedTransferQueue<Message> getMessageQueue() {
        return messageQueue;
    }

    public void postMessageToQueue(Message message) {
        this.messageQueue.put(message);
    }
}
