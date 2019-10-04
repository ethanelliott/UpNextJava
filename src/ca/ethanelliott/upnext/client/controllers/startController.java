package ca.ethanelliott.upnext.client.controllers;

import ca.ethanelliott.upnext.client.UpNext;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Objects;
import java.util.ResourceBundle;

public class startController implements Initializable {
    @FXML
    public Button startButton;
    @FXML
    public PasswordField adminPassword;
    @FXML
    public TextField partyName;
    @FXML
    public Button backButton;
    @FXML
    private URL location;

    public startController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.startButton.setDisable(true);
    }

    @FXML
    public void startParty(ActionEvent event) throws IOException {
        String partyName = this.partyName.getText();
        String adminPassword = this.adminPassword.getText();

        UpNext.getInstance().interSceneObject = new HashMap<String, Object>(){{
            put("name", partyName);
            put("password", adminPassword);
        }};

        Parent view = FXMLLoader.load(Objects.requireNonNull
                (getClass()
                        .getClassLoader()
                        .getResource("ca/ethanelliott/upnext/client/interfaces/spotifyAuth.fxml")
                )
        );
        Scene scene = new Scene(view);
        scene.getStylesheets().add(String.valueOf(this.getClass().getClassLoader().getResource("ca/ethanelliott/upnext/client/interfaces/style/style.css")));
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    public void checkInput(KeyEvent keyEvent) {
        if (this.partyName.getText().length() > 0 && this.adminPassword.getText().length() > 0) {
            this.startButton.setDisable(false);
        } else {
            this.startButton.setDisable(true);
        }
    }

    @FXML
    public void goBack(ActionEvent event) throws IOException {
        Parent view = FXMLLoader.load(Objects.requireNonNull
                (getClass()
                        .getClassLoader()
                        .getResource("ca/ethanelliott/upnext/client/interfaces/landing.fxml")
                )
        );
        Scene scene = new Scene(view);
        scene.getStylesheets().add(String.valueOf(this.getClass().getClassLoader().getResource("ca/ethanelliott/upnext/client/interfaces/style/style.css")));
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
}
