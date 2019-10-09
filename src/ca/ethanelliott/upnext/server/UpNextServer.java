package ca.ethanelliott.upnext.server;

import ca.ethanelliott.upnext.server.database.Database;
import ca.ethanelliott.upnext.server.socket.Messenger;
import ca.ethanelliott.upnext.server.socket.SocketServer;
import ca.ethanelliott.upnext.server.upnext.UpNext;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class UpNextServer extends Application {
    public static int SOCKET_SERVER_PORT = 8888;

    private String[] args;

    public static void main(String[] args) {
        new UpNextServer().run(args);
    }

    private void run(String[] args) {
        Messenger.getInstance();
        UpNext.getInstance();
        SocketServer.getInstance(SOCKET_SERVER_PORT);

        //Start the interface
        new Thread(() -> {
            launch(args);
            System.exit(0);
        }).start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));
    }

    @Override
    public void start(Stage window) throws Exception {
        window.setTitle("UpNext Server");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getClassLoader().getResource("ca/ethanelliott/upnext/server/interfaces/main.fxml"));
        HBox root = loader.load();
        Scene scene = new Scene(root);
        window.setResizable(false);
        window.setScene(scene);
        window.getIcons().add(new Image(String.valueOf(this.getClass().getClassLoader().getResource("ca/ethanelliott/upnext/resources/music_note.png"))));
        window.show();
    }
}
