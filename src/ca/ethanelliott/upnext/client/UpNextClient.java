package ca.ethanelliott.upnext.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class UpNextClient extends Application {
    public static void main(String[] args) {
        System.out.println("UPNEXT CLIENT RUNNING");
        UpNext main = UpNext.getInstance();
        launch(args);
        System.exit(0);
    }

    @Override
    public void start(Stage window) throws Exception {
        window.setTitle("UpNext Client");
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("ca/ethanelliott/upnext/client/interfaces/landing.fxml")));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(String.valueOf(this.getClass().getClassLoader().getResource("ca/ethanelliott/upnext/client/interfaces/style/style.css")));
        window.setScene(scene);
        window.getIcons().add(new Image(String.valueOf(this.getClass().getClassLoader().getResource("ca/ethanelliott/upnext/resources/music_note.png"))));
        window.show();
    }
}
