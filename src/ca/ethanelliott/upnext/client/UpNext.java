package ca.ethanelliott.upnext.client;

import ca.ethanelliott.upnext.server.socket.Message;
import ca.ethanelliott.upnext.server.upnext.Party;
import javafx.application.Platform;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class UpNext implements Runnable{
    private static UpNext instance = null;
    public static UpNext getInstance() {
        if (instance == null) {
            instance = new UpNext();
        }
        return instance;
    }

    private Socket server;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Thread consumer;
    private Thread producer;

    private Map<String, Function<Message, Object>> socketEventLookup;

    private String address;

    private String partyID;
    private String partyCode;
    public Object interSceneObject;

    public Map<String, Function<Message, Object>> getSocketEventLookup() {
        return socketEventLookup;
    }

    public String getPartyID() {
        return partyID;
    }

    public void setPartyID(String partyID) {
        this.partyID = partyID;
    }

    private UpNext() {
        try {
            this.socketEventLookup = new HashMap<>();
            this.server = new Socket("localhost", 8888);
            this.out = new ObjectOutputStream(server.getOutputStream());
            this.in = new ObjectInputStream(server.getInputStream());
            Message input = (Message) this.in.readObject();
            if (input.getData().getEventIdentifier().equals("get-address")) {
                this.address = (String) input.getData().getData();
            }
            this.run();
        } catch (IOException e) {
            System.exit(0);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        // Start consumer thread
        this.consumer = new Thread(() -> {
            try {
                while (true) {
                    try {
                        Message input = (Message) this.in.readObject();
                        this.processEvents(input);
                    } catch (IOException | ClassNotFoundException e) {
                        System.exit(0);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    this.server.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                System.exit(0);
            }
        });
        this.consumer.start();
    }

    private void processEvents(Message message) {
        String eventIdentifier = message.getData().getEventIdentifier();
        if (this.socketEventLookup.containsKey(eventIdentifier)) {
            this.socketEventLookup.get(eventIdentifier).apply(message);
        } else {
            throw new IllegalStateException("Unknown event identifier: " + eventIdentifier);
        }
    }

    public void sendMessage(String eventIdentifier, Object data) throws IOException {
        this.out.writeObject(new Message(this.address, "*", eventIdentifier, data));
    }

    public void startEventLoop() {
        if (this.producer == null) {
            this.producer = new Thread(() -> {
                // This is the producer thread
                try {
                    while (!Thread.interrupted()) {
                        Thread.sleep(800);
                        try {
                            Message m = new Message(this.address, "*", "event-loop", this.partyID);
                            this.out.writeObject(m);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            this.producer.start();
        } else {
            System.out.println("EVENT LOOP ALREADY RUNNING");
        }
    }

    public void on(String eventIdentifier, Function<Message, Object> eventHandler) {
        this.socketEventLookup.put(eventIdentifier, eventHandler);
    }
}
