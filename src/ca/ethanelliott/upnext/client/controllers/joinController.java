package ca.ethanelliott.upnext.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class joinController implements Initializable {
    @FXML
    public TextField partyCode;

    @FXML
    public TextField userName;

    @FXML
    public Button joinButton;

    @FXML
    private URL location;

    public joinController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.joinButton.setDisable(true);
    }

    @FXML
    public void joinParty(ActionEvent event) {
        String partyCode = this.partyCode.getText().toUpperCase();
        String nickName = this.partyCode.getText();
        System.out.println(partyCode);
    }

    @FXML
    public void checkInput(KeyEvent keyEvent) {
        if (this.partyCode.getText().length() == 4 && (this.userName.getText().length() > 0 && this.userName.getText().length() < 12)) {
            this.joinButton.setDisable(false);
        } else {
            this.joinButton.setDisable(true);
        }
    }
}
