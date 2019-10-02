package ca.ethanelliott.upnext.server.controllers;

import ca.ethanelliott.upnext.server.UpNextServer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;

public class FxFXMLController {


    @FXML
    public Label clientCount;

    @FXML
    public Label portNumber;

    @FXML
    public Button stopButton;

    @FXML
    private Label isRunning;

    @FXML
    private URL location;

    public FxFXMLController() {}

    @FXML
    private void initialize() {

        this.isRunning.setText("RUNNING");
        this.portNumber.setText(String.valueOf(UpNextServer.SOCKET_SERVER_PORT));
    }

    @FXML
    private void stopServer() {
        this.isRunning.setText("STOPPING");
        System.exit(0);
    }
}
