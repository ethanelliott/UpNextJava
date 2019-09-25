package ca.ethanelliott.upnext.socket;

// Controls the individual connection to the client and their operations

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;

/**
 * Each Socket thread gets an associated address that can be associated to
 * a direction that the message must flow when it needs to be delivered
 * This means that the server has a central lookup for an address and a
 * message queue that messages can be sent to
 *
 * Each socket thread is going to have a receive message queue and a send
 * message queue.
 *
 * Send MQ -> send messages from the socket to the server to be processed
 * Recv MQ -> Individual per thread for the server to determine where to
 *            send directed messages
 *
 * Somehow, the socket is going to receive event strings that can be
 * associated to an action on the server and vice-versa
 */

public class SocketThread extends Thread {
    private LinkedTransferQueue<Message> messageQueue;
    private Socket client;
    private PrintWriter out;
    private BufferedReader in;

    SocketThread(Socket clientSocket, LinkedTransferQueue<Message> messageQueue) {
        this.client = clientSocket;
        this.messageQueue = messageQueue;
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
            Random random = new Random(1);
            try
            {
                while (true)
                {
                    Integer messageData = random.nextInt();
                    Message m = new Message("1", "2", "event-id", messageData);
                    boolean added = this.messageQueue.tryTransfer(m);
                    if(added) {
                        System.out.println("Producer added the message - " + messageData);
                    }
                    Thread.sleep(TimeUnit.SECONDS.toMillis(1));
                }
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
