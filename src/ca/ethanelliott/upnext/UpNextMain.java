package ca.ethanelliott.upnext;

import ca.ethanelliott.upnext.socket.Message;
import ca.ethanelliott.upnext.socket.Messenger;
import ca.ethanelliott.upnext.socket.SocketServer;
import ca.ethanelliott.upnext.upnext.UpNext;
//import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Scene;
//import javafx.scene.layout.HBox;
//import javafx.stage.Stage;

public class UpNextMain /*extends Application*/ implements Runnable {
    public static int SOCKET_SERVER_PORT = 8888;

    public static void main(String[] args) {
        new UpNextMain().run();
    }

    @Override
    public void run() {
        //Start the interface
        //launch(args);
        Messenger.getInstance();
        UpNext.getInstance();
        SocketServer.getInstance(SOCKET_SERVER_PORT);


        System.out.println("UPNEXT SERVER RUNNING");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting Down");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("UpNext Server Stopped");
        }));
    }

//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        primaryStage.setTitle("UpNext Server");
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(this.getClass().getClassLoader().getResource("resources/main.fxml"));
//        HBox root = loader.<HBox>load();
//        Scene scene = new Scene(root);
//        primaryStage.setResizable(false);
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
}
