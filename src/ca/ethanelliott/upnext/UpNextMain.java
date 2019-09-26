package ca.ethanelliott.upnext;

import ca.ethanelliott.upnext.database.Database;
import ca.ethanelliott.upnext.socket.Message;
import ca.ethanelliott.upnext.socket.Messenger;
import ca.ethanelliott.upnext.socket.SocketServer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import javax.xml.crypto.Data;
import java.awt.image.DataBuffer;
import java.util.concurrent.LinkedTransferQueue;

public class UpNextMain extends Application {
    public static int SOCKET_SERVER_PORT = 8888;

    public static void main(String[] args) {
        System.out.println("UPNEXT SERVER RUNNING");

        Database d = Database.getInstance();

        SocketServer x = new SocketServer(SOCKET_SERVER_PORT);

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
