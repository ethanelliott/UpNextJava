package ca.ethanelliott.upnext.upnext;

import ca.ethanelliott.upnext.database.Database;
import ca.ethanelliott.upnext.socket.Message;

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
    private LinkedTransferQueue<Message> messageQueue;

    private UpNext() {
        database = Database.getInstance();
        messageQueue = new LinkedTransferQueue<>();
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
