package ca.ethanelliott.upnext.server.socket;

/**
 * This class is literally just a consumer/producer interface for the messenger...
 * It has its own message queue that it can pull from, and serialize to be sent over the
 * socket connection. This means that the messenger just knows where to send the message as a
 * transient object, but this class does the heavy lifting, all on separate threads :)
 * <p>
 * It has the message queue, an identifier, a read/write for the socket, and a reference
 * to the messenger
 * <p>
 * and 2 threads
 * - one for reading from the socket
 * - reads the message, de-serialize, push to the messenger queue
 * - one for writing to the socket
 * - reads from the message queue, serialize, write to the socket
 * <p>
 * messenger posts the directed message to the queue, the thread loop checks for
 * a message and then serializes and
 */

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
        System.out.println("Creating new Socket Thread @ " + uuid);
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
        System.out.println("New Socket Thread");

        // Start consumer thread
        this.consumer = new Thread(() -> {
            try {
                System.out.println("Starting Consumer");
                while (!Thread.interrupted()) {
                    try {
                        Message input = (Message) this.in.readObject();
                        messenger.postMessage(input);
                    } catch (IOException | ClassNotFoundException e) {
                        this.terminate();
                    }

                }
            } catch (Exception e) {
                System.out.println("UNHANDLED ERROR IN CONSUMER");
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
            System.out.println("Starting Producer");
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
                System.out.println("UNHANDLED ERROR IN PRODUCER");
            }
        });


        this.consumer.start();
        this.producer.start();
    }

    private void terminate() {
        System.out.println("SOCKET DISCONNECT - TERMINATE THREAD");
        SocketServer.getInstance(0).clientCount--;
        this.producer.interrupt();
        this.consumer.interrupt();
        this.interrupt();
    }
}