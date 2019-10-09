package ca.ethanelliott.upnext.server.socket;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedTransferQueue;

public class Messenger {
    private static Messenger instance = null;

    public static Messenger getInstance() {
        if (instance == null) {
            instance = new Messenger();
        }
        return instance;
    }

    private Messenger() {
        this.messageQueue = new LinkedTransferQueue<>();
        this.addressLookup = new HashMap<>();
    }

    private Map<String, LinkedTransferQueue<Message>> addressLookup;

    private LinkedTransferQueue<Message> messageQueue;

    public void postMessage(Message message) {
        this.messageQueue.add(message);
    }

    public Message getMessage() {
        return this.messageQueue.poll();
    }

    public Map<String, LinkedTransferQueue<Message>> getAddressLookup() {
        return this.addressLookup;
    }

    public boolean registerNewAddress(String address) {
        // On new address, create an entry in the lookup with the associated LTQ
        if (this.addressLookup.containsKey(address)) {
            return false;
        }
        this.addressLookup.put(address, new LinkedTransferQueue<>());
        return true;
    }

    public LinkedTransferQueue<Message> getQueueByAddress(String address) {
        return this.addressLookup.getOrDefault(address, null);
    }

    public void postToQueueByAddress(String address, Message message) {
        this.getQueueByAddress(address).put(message);
    }

    public Message pollFromQueueByAddress(String address) {
        return this.getQueueByAddress(address).poll();
    }
}