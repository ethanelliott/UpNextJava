package ca.ethanelliott.upnext.server.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.UUID;


public class SocketThread extends Thread {
    private String uuid;
    private Messenger messenger;
    private Socket client;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Thread consumer;
    private Thread producer;

    SocketThread(Socket clientSocket, Messenger messenger) {
        this.client = clientSocket;
        this.messenger = messenger;
        uuid = UUID.randomUUID().toString();
        Messenger.getInstance().registerNewAddress(uuid);
        try {
            this.out = new ObjectOutputStream(this.client.getOutputStream());
            this.in = new ObjectInputStream(this.client.getInputStream());
            this.out.writeObject(new Message("server", uuid, "get-address", uuid));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.start();
    }

    public void run() {

        // Start consumer thread
        this.consumer = new Thread(() -> {
            try {
                while (!Thread.interrupted()) {
                    try {
                        Message input = (Message) this.in.readObject();
                        messenger.postMessage(input);
                    } catch (IOException | ClassNotFoundException e) {
                        this.terminate();
                    }

                }
            } catch (Exception e) {
                try {
                    this.client.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                this.terminate();
            }
        });

        this.producer = new Thread(() -> {
            // This is the producer thread
            try {
                while (!Thread.interrupted()) {
                    try {
                        if (this.messenger.getQueueByAddress(this.uuid).size() > 0) {
                            Message message = this.messenger.getQueueByAddress(this.uuid).take();
                            this.out.writeObject(message);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        this.consumer.start();
        this.producer.start();
    }

    private void terminate() {
        SocketServer.getInstance(0).clientCount--;
        this.producer.interrupt();
        this.consumer.interrupt();
        this.interrupt();
    }
}