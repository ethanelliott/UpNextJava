package ca.ethanelliott.upnext.client;

import ca.ethanelliott.upnext.server.socket.Message;
import ca.ethanelliott.upnext.server.upnext.Party;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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

    private String address;

    private String partyID;

    public Object interSceneObject;

    // This object will also need to know about what party it is in and its nickname

    private UpNext() {
        try {
            this.server = new Socket("localhost", 8888);
            this.out = new ObjectOutputStream(server.getOutputStream());
            this.in = new ObjectInputStream(server.getInputStream());
            Message input = (Message) this.in.readObject();
            if (input.getData().getEventIdentifier().equals("get-address")) {
                this.address = (String) input.getData().getData();
            }
            System.out.println(this.address);
            this.run();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("YIKES NO SERVER RUNNING!");
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
                System.out.println("Starting Consumer");
                while (true) {
                    try {
                        Message input = (Message) this.in.readObject();
                        this.processEvents(input);
                    } catch (IOException | ClassNotFoundException e) {
                        System.out.println("LOOKS LIKE THE SERVER IS NOT RUNNING");
                        System.exit(0);
                    }
                }
            } catch (Exception e) {
                System.out.println("UNHANDLED ERROR IN CONSUMER");
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
        switch(message.getData().getEventIdentifier()) {
            case "party-created":
                Party party = (Party) message.getData().getData();
                System.out.println(party.getName() + " | " + party.getCode() + " | " + party.getUuid());
                this.partyID = party.getUuid();
                UpNext.getInstance().startEventLoop();
                break;
            case "event-loop-response":
                this.eventLoopResponse(message);
                break;
            default:
                System.out.println("Unknown event identifier :(");
                break;
        }
    }

    public void sendMessage(String eventIdentifier, Object data) throws IOException {
        this.out.writeObject(new Message(this.address, "*", eventIdentifier, data));
    }

    public void startEventLoop() {
        if (this.producer == null) {
            this.producer = new Thread(() -> {
                // This is the producer thread
                System.out.println("Starting EVENT LOOP");
                try {
                    while (true) {
                        Thread.sleep(100);
                        try {
                            Message m = new Message(this.address, "*", "event-loop", this.partyID);
                            this.out.writeObject(m);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    System.out.println("UNHANDLED ERROR IN PRODUCER");
                }
            });
            this.producer.start();
        } else {
            System.out.println("EVENT LOOP ALREADY RUNNING");
        }
    }

    private void eventLoopResponse(Message message) {
        Party party = (Party) message.getData().getData();

    }
}
