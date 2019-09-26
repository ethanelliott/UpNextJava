package ca.ethanelliott.upnext.socket;

// Controls the individual connection to the client and their operations

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;

/**
 * Each Socket thread gets an associated address that can be associated to
 * a direction that the message must flow when it needs to be delivered
 * This means that the server has a central lookup for an address and a
 * message queue that messages can be sent to
 * <p>
 * Each socket thread is going to have a receive message queue and a send
 * message queue.
 * <p>
 * Send MQ -> send messages from the socket to the server to be processed
 * Recv MQ -> Individual per thread for the server to determine where to
 * send directed messages
 * <p>
 * Somehow, the socket is going to receive event strings that can be
 * associated to an action on the server and vice-versa
 */

public class SocketThread extends Thread {
    String uuid;
    private Socket client;
    private PrintWriter out;
    private BufferedReader in;

    SocketThread(Socket clientSocket) {
        this.client = clientSocket;
        uuid = UUID.randomUUID().toString();
        Messenger.getInstance().addNewAddress(uuid);
        try {
            this.out = new PrintWriter(this.client.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.start();
    }

    public void run() {
        try {
            System.out.println("STARTING PRODUCER");
            Random random = new Random(new Date().getTime());
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(1));
                Integer messageData = random.nextInt();
                Message m = new Message(uuid, "*", "event-id", messageData);
                Messenger.getInstance().postToQueueByAddress(uuid, m);
                System.out.println("Producer added the message - " + messageData);
                Thread.sleep(TimeUnit.SECONDS.toMillis(1));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.client.close();
        } catch (IOException e) {
            System.out.println("Exception caught...");
            System.out.println(e.getMessage());
        }
    }
}
