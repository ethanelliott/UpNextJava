package ca.ethanelliott.upnext.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedTransferQueue;

public class SocketServer extends Thread {
    private LinkedTransferQueue<Integer> messageQueue;
    private int portNumber;
    private ServerSocket serverSocket;

    public SocketServer(int portNumber, LinkedTransferQueue<Integer> messageQueue) {
        this.portNumber = portNumber;
        this.messageQueue = messageQueue;
        try {
            this.serverSocket = new ServerSocket(this.portNumber);
            this.start();
        } catch (IOException e) {
            // Failed to create the socket
            e.printStackTrace();
        }
    }


    public void run() {
        while (true) {
            try {
                Socket clientConnection = this.serverSocket.accept();
                System.out.println("NEW CLIENT: " + clientConnection.getRemoteSocketAddress());
                SocketThread s = new SocketThread(clientConnection, this.messageQueue);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public int getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }
}
