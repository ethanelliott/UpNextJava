package ca.ethanelliott.upnext;

import ca.ethanelliott.upnext.socket.SocketServer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;

public class UpNextMain extends Application {
    private static int SOCKET_SERVER_PORT = 8888;

    public static void main(String[] args) {
        System.out.println("UPNEXT SERVER RUNNING");

        LinkedTransferQueue<Integer> linkedTransferQueue = new LinkedTransferQueue<>();

        // Local consumer
        new Thread(() ->
        {
            try
            {
                while (true)
                {
                    System.out.println("Consumer is waiting to take message...");
                    Integer message = linkedTransferQueue.take();
                    System.out.println("Consumer recieved the message - " + message);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        //start the server
        SocketServer x = new SocketServer(SOCKET_SERVER_PORT, linkedTransferQueue);

        //Start the interface
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("UpNext Server");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getClassLoader().getResource("resources/main.fxml"));
        HBox root = loader.<HBox>load();
        Scene scene = new Scene(root);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
