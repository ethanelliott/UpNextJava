package ca.ethanelliott.upnext.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class landingController implements Initializable {
    @FXML
    public Button joinButton;
    @FXML
    public Button startButton;
    @FXML
    private URL location;

    public landingController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void joinParty(ActionEvent event) throws IOException {
        Parent view = FXMLLoader.load(Objects.requireNonNull
                (getClass()
                        .getClassLoader()
                        .getResource("ca/ethanelliott/upnext/client/interfaces/join.fxml")
                )
        );
        Scene scene = new Scene(view);
        scene.getStylesheets().add(String.valueOf(this.getClass().getClassLoader().getResource("ca/ethanelliott/upnext/client/interfaces/style/style.css")));
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    private void startParty(ActionEvent event) throws IOException {
        Parent view = FXMLLoader.load(Objects.requireNonNull
                (getClass()
                        .getClassLoader()
                        .getResource("ca/ethanelliott/upnext/client/interfaces/start.fxml")
                )
        );
        Scene scene = new Scene(view);
        scene.getStylesheets().add(String.valueOf(this.getClass().getClassLoader().getResource("ca/ethanelliott/upnext/client/interfaces/style/style.css")));
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
}
