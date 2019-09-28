package ca.ethanelliott.upnext.socket;

/**
 * This class is literally just a consumer/producer interface for the messenger...
 * It has its own message queue that it can pull from, and serialize to be sent over the
 * socket connection. This means that the messenger just knows where to send the message as a
 * transient object, but this class does the heavy lifting, all on separate threads :)
 *
 * It has the message queue, an identifier, a read/write for the socket, and a reference
 * to the messenger
 *
 * and 2 threads
 * - one for reading from the socket
 *      - reads the message, de-serialize, push to the messenger queue
 * - one for writing to the socket
 *      - reads from the message queue, serialize, write to the socket
 *
 * messenger posts the directed message to the queue, the thread loop checks for
 * a message and then serializes and
 *
 */

import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


public class SocketThread extends Thread {
    private String uuid;
    private Messenger messenger;
    private Socket client;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    SocketThread(Socket clientSocket, Messenger messenger) {
        this.client = clientSocket;
        this.messenger = messenger;
        uuid = UUID.randomUUID().toString();
        Messenger.getInstance().addNewAddress(uuid);
        try {
            this.out = new ObjectOutputStream(this.client.getOutputStream());
            this.in = new ObjectInputStream(this.client.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.start();
    }

    public void run() {
        System.out.println("New Socket Thread");

        // Start consumer thread
        new Thread(() -> {
            while (true) {
                try {
                    Message input = (Message) this.in.readObject();
                    messenger.postMessage(input);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // This is the producer thread
        System.out.println("STARTING PRODUCER");
        Random random = new Random(new Date().getTime());
        while (true) {
            try {
                Message m = new Message(uuid, "*", "event-id", random.nextInt(100));
                this.out.writeObject(m);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

//        try {
//
//            try {
//                Thread.sleep(TimeUnit.SECONDS.toMillis(1));
//                Integer messageData = random.nextInt();
//
//                Messenger.getInstance().postToQueueByAddress(uuid, m);
//                System.out.println("Producer added the message - " + messageData);
//                Thread.sleep(TimeUnit.SECONDS.toMillis(1));
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            this.client.close();
//        } catch (IOException e) {
//            System.out.println("Exception caught...");
//            System.out.println(e.getMessage());
//        }
    }
}
