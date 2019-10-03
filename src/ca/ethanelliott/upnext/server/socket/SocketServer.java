package ca.ethanelliott.upnext.server.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer extends Thread {
    private static SocketServer instance = null;
    public static SocketServer getInstance(int portNumber) {
        if (instance == null) {
            System.out.println("Creating new Socket Server");
            instance = new SocketServer(portNumber);
        }
        return instance;
    }

    private int portNumber;
    private ServerSocket serverSocket;
    public int clientCount;

    public SocketServer(int portNumber) {
        this.portNumber = portNumber;
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
                this.clientCount++;
                System.out.println("NEW CLIENT: " + clientConnection.getRemoteSocketAddress());
                SocketThread s = new SocketThread(clientConnection, Messenger.getInstance());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public int getPortNumber() {
        return portNumber;
    }

    public int getClientCount() {
        return clientCount;
    }
}