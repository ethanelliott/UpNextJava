package ca.ethanelliott.upnext.client.controllers;

import ca.ethanelliott.upnext.client.UpNext;
import ca.ethanelliott.upnext.server.upnext.Party;
import ca.ethanelliott.upnext.server.upnext.PlaylistEntry;
import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class queueController implements Initializable {
    @FXML
    public VBox queue;
    @FXML
    private URL location;

    private Thread mainLoop;

    public queueController() {
    }

    private ArrayList<PlaylistEntry> playlist = new ArrayList<>();

    @FXML
    public void openSearch(ActionEvent event) throws IOException {
        Parent view = FXMLLoader.load(Objects.requireNonNull
                (getClass()
                        .getClassLoader()
                        .getResource("ca/ethanelliott/upnext/client/interfaces/search.fxml")
                )
        );
        Scene scene = new Scene(view);
        Stage queue = new Stage();
        scene.getStylesheets().add(String.valueOf(this.getClass().getClassLoader().getResource("ca/ethanelliott/upnext/client/interfaces/style/style.css")));
        queue.initModality(Modality.WINDOW_MODAL);
        queue.initStyle(StageStyle.UNIFIED);
        queue.setTitle("UpNext Search");
        queue.getIcons().add(new Image(String.valueOf(this.getClass().getClassLoader().getResource("ca/ethanelliott/upnext/resources/music_note.png"))));
        queue.setScene(scene);
        queue.show();
    }

    public static class HBoxCell extends HBox {
        Label label = new Label();
        Button upvoteButton = new Button();
        Label upvoteCount = new Label();
        Button downvoteButton = new Button();

        HBoxCell(String songName, int upvoteValue) {
            super();

            label.setText(songName);
            label.setMaxWidth(Double.MAX_VALUE);
            label.getStyleClass().add("white-text");
            HBox.setHgrow(label, Priority.ALWAYS);
            this.setAlignment(Pos.CENTER_LEFT);

            upvoteCount.setText(String.valueOf(upvoteValue));
            upvoteCount.getStyleClass().add("white-text");
            upvoteCount.getStyleClass().add("pad-me");

            ImageView upArrow = new ImageView(new Image(String.valueOf(this.getClass().getClassLoader().getResource("ca/ethanelliott/upnext/resources/up.png"))));
            ImageView downArrow = new ImageView(new Image(String.valueOf(this.getClass().getClassLoader().getResource("ca/ethanelliott/upnext/resources/down.png"))));
            upArrow.setFitHeight(16);
            downArrow.setFitHeight(16);
            upArrow.setPreserveRatio(true);
            downArrow.setPreserveRatio(true);
            upvoteButton.setGraphic(upArrow);
            downvoteButton.setGraphic(downArrow);
            upvoteButton.getStyleClass().add("button-outline");
            downvoteButton.getStyleClass().add("button-outline");
            this.getChildren().addAll(label, upvoteButton, upvoteCount, downvoteButton);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UpNext main = UpNext.getInstance();
        this.startQueueLoop();
        main.on("give-queue", message -> {
            Party party = (Party) message.getData().getData();
            System.out.println(new Gson().toJson(party));
//            playlist = (ArrayList<PlaylistEntry>) message.getData().getData();
//            Platform.runLater(this::drawList);
            return null;
        });
    }

    private void drawList() {
        queue.getChildren().clear();
        for (PlaylistEntry playlistEntry : this.playlist) {
            queue.getChildren().add(new HBoxCell(playlistEntry.getName(), (int) playlistEntry.getVotes()));
        }
    }

    public void startQueueLoop() {
        System.out.println("Start Queue Loop");
        if (this.mainLoop == null) {
            this.mainLoop = new Thread(() -> {
                try {
                    while (!Thread.interrupted()) {
                        try {
                            UpNext.getInstance().sendMessage("get-queue", UpNext.getInstance().getPartyID());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Thread.sleep(5000);
                    }
                } catch (Exception e) {
                    System.out.println("UNHANDLED ERROR IN QueueLoop");
                }
            });
            this.mainLoop.start();
        } else {
            System.out.println("QueueLoop ALREADY RUNNING");
        }
    }

}
