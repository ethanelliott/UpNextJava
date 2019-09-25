package ca.ethanelliott.upnext.socket;

import java.util.concurrent.LinkedTransferQueue;

public class SocketMessenger {
    private static SocketMessenger instance = null;
    public static SocketMessenger getInstance() {
        if (instance == null) { instance = new SocketMessenger(); }
        return instance;
    }
    private SocketMessenger() {
        this.messageQueue = new LinkedTransferQueue<>();
    }

    private LinkedTransferQueue<Message> messageQueue;

    public void postMessage(Message message) {
        this.messageQueue.add(message);
    }

    public Message getMessage() {
        return this.messageQueue.poll();
    }
}
