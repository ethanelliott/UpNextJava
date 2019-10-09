package ca.ethanelliott.upnext.client.controllers;

import ca.ethanelliott.upnext.client.UpNext;
import ca.ethanelliott.upnext.server.spotify.types.PlaylistObject;
import ca.ethanelliott.upnext.server.upnext.Party;
import ca.ethanelliott.upnext.server.upnext.PlaylistEntry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.org.apache.xerces.internal.xs.datatypes.ObjectList;
import javafx.application.Platform;
import javafx.beans.binding.ObjectExpression;
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
        HBox infoContainer = new HBox();
        VBox titleContainer = new VBox();
        Label song = new Label();
        Label artist = new Label();
        ImageView artwork = new ImageView();
        Button upvoteButton = new Button();
        Label upvoteCount = new Label();
        Button downvoteButton = new Button();

        HBoxCell(String songID, String songName, String artistName, String albumArtwork, int upvoteValue) {
            super();

            song.setText(songName);
            song.setMaxWidth(Double.MAX_VALUE);
            song.getStyleClass().addAll("white-text", "title");
            HBox.setHgrow(song, Priority.ALWAYS);
            this.setAlignment(Pos.CENTER_LEFT);

            artist.setText(artistName);
            artist.setMaxWidth(Double.MAX_VALUE);
            artist.getStyleClass().add("white-text");


            HBox.setHgrow(infoContainer, Priority.ALWAYS);
            this.setAlignment(Pos.CENTER_LEFT);
            titleContainer.getChildren().addAll(song, artist);
            titleContainer.getStyleClass().add("info-padding");

            HBox.setHgrow(titleContainer, Priority.ALWAYS);
            artwork.setImage(new Image(albumArtwork));
            artwork.setFitHeight(50);
            artwork.setPreserveRatio(true);
            infoContainer.getChildren().addAll(artwork, titleContainer);

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
            upvoteButton.getStyleClass().add("button-flat");
            downvoteButton.getStyleClass().add("button-flat");

            upvoteButton.setOnAction((event) -> {
                UpNext main = UpNext.getInstance();
                Map<String, Object> data = new HashMap<>();
                data.put("song-id", songID);
                data.put("party-id", UpNext.getInstance().getPartyID());
                try {
                    main.sendMessage("upvote-song", data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            downvoteButton.setOnAction((event) -> {
                UpNext main = UpNext.getInstance();
                Map<String, Object> data = new HashMap<>();
                data.put("song-id", songID);
                data.put("party-id", UpNext.getInstance().getPartyID());
                try {
                    main.sendMessage("downvote-song", data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            this.getChildren().addAll(infoContainer, upvoteButton, upvoteCount, downvoteButton);
            this.getStyleClass().add("artwork-padding");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UpNext main = UpNext.getInstance();
        this.getQueue();
        main.on("give-queue", message -> {
            String data = (String) message.getData().getData();
            this.playlist = new Gson().fromJson(data, new TypeToken<ArrayList<PlaylistEntry>>(){}.getType());
            Platform.runLater(this::drawList);
            return null;
        });
    }

    private void drawList() {
        queue.getChildren().clear();
        this.playlist.sort((a, b) -> b.getVotes() - a.getVotes());
        for (PlaylistEntry playlistEntry : this.playlist) {
            String id = playlistEntry.getId();
            String name = playlistEntry.getName();
            int votes = playlistEntry.getVotes();
            queue.getChildren().add(new HBoxCell(id, name, playlistEntry.getArtist(), playlistEntry.getArtwork(), votes));
        }
    }

    public void getQueue() {
        try {
            UpNext.getInstance().sendMessage("get-queue", UpNext.getInstance().getPartyID());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
