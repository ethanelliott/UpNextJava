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

public class SocketThread extends Thread {
    private LinkedTransferQueue<Integer> messageQueue;
    private Socket client;
    private PrintWriter out;
    private BufferedReader in;

    SocketThread(Socket clientSocket, LinkedTransferQueue<Integer> messageQueue) {
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
                    Integer message = random.nextInt();
                    boolean added = this.messageQueue.tryTransfer(message);
                    if(added) {
                        System.out.println("Producer added the message - " + message);
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
