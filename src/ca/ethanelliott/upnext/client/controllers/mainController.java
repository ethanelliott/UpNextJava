package ca.ethanelliott.upnext.client.controllers;

import ca.ethanelliott.upnext.client.UpNext;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class mainController implements Initializable {
    @FXML
    private URL location;

    public mainController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UpNext main = UpNext.getInstance();
    }
}
