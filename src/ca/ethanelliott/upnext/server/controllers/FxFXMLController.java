package ca.ethanelliott.upnext.server.controllers;

import ca.ethanelliott.upnext.server.socket.SocketServer;
import javafx.application.Platform;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.concurrent.atomic.AtomicInteger;

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

    public FxFXMLController() {
    }

    private int oldClientCount = 0;

    @FXML
    private void initialize() {
        SocketServer socketServer = SocketServer.getInstance(0);
        this.isRunning.setText("RUNNING");
        this.portNumber.textProperty().bind(new SimpleIntegerProperty(socketServer.getPortNumber()).asString());
        new Thread(() -> {
            while (!Thread.interrupted()) {
                if (oldClientCount != socketServer.getClientCount()) {
                    oldClientCount = socketServer.getClientCount();
                    Platform.runLater(() -> {
                        clientCount.setText(String.valueOf(oldClientCount));
                    });
                }
            }
        }).start();
    }


    @FXML
    private void stopServer() {
        this.isRunning.setText("STOPPING");
        System.exit(0);
    }
}
