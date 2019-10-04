package ca.ethanelliott.upnext.client.controllers;

import ca.ethanelliott.upnext.client.UpNext;
import ca.ethanelliott.upnext.server.socket.Message;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class joinController implements Initializable {
    @FXML
    public TextField partyCode;
    @FXML
    public TextField userName;
    @FXML
    public Button joinButton;
    @FXML
    public Button backButton;
    @FXML
    public Label errorText;
    @FXML
    private URL location;

    public joinController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.joinButton.setDisable(true);
        UpNext main = UpNext.getInstance();
    }

    @FXML
    public void joinParty(ActionEvent event) {
        String partyCode = this.partyCode.getText().toUpperCase();
        String nickName = this.partyCode.getText();
        UpNext main = UpNext.getInstance();
        main.on("party-code-good", (Message message) -> {
            this.login(event, (String) message.getData().getData());
            return null;
        });
        main.on("party-code-bad", (Message message) -> {
            Platform.runLater(() -> errorText.setText("Error: invalid party code!"));
            return null;
        });
        try {
            main.sendMessage("validate-party-code", partyCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void login(ActionEvent event, String partyID) {
        // Tell the server to create a new user
        Platform.runLater(() -> {
            try {
                UpNext.getInstance().setPartyID(partyID);
                this.goToMain(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    public void checkInput(KeyEvent keyEvent) {
        if (this.partyCode.getText().length() == 4 && (this.userName.getText().length() > 0 && this.userName.getText().length() < 12)) {
            this.joinButton.setDisable(false);
        } else {
            this.joinButton.setDisable(true);
        }
    }

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

    public void goToMain(ActionEvent event) throws IOException {
        Parent view = FXMLLoader.load(Objects.requireNonNull
                (getClass()
                        .getClassLoader()
                        .getResource("ca/ethanelliott/upnext/client/interfaces/main.fxml")
                )
        );
        Scene scene = new Scene(view);
        scene.getStylesheets().add(String.valueOf(this.getClass().getClassLoader().getResource("ca/ethanelliott/upnext/client/interfaces/style/style.css")));
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
}
