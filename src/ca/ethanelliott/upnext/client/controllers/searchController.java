package ca.ethanelliott.upnext.client.controllers;

import ca.ethanelliott.upnext.client.UpNext;
import ca.ethanelliott.upnext.server.spotify.types.PagingObject;
import ca.ethanelliott.upnext.server.spotify.types.SearchResultObject;
import ca.ethanelliott.upnext.server.spotify.types.TrackObject;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import javax.sound.midi.Track;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class searchController implements Initializable {
    @FXML
    public TextField searchTerms;
    @FXML
    public VBox searchResults;
    @FXML
    private URL location;

    public searchController() {
    }

    public static class HBoxCell extends HBox {
        HBox infoContainer = new HBox();
        VBox titleContainer = new VBox();
        Label song = new Label();
        Label artist = new Label();
        ImageView artwork = new ImageView();
        Button addButton = new Button();

        HBoxCell(String songName, String artistName, String albumArtwork, String songID) {
            super();

            song.setText(songName);
            song.setMaxWidth(Double.MAX_VALUE);
            song.getStyleClass().addAll("white-text", "title");

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

            ImageView upArrow = new ImageView(new Image(String.valueOf(this.getClass().getClassLoader().getResource("ca/ethanelliott/upnext/resources/add.png"))));
            upArrow.setFitHeight(16);
            upArrow.setPreserveRatio(true);
            addButton.setGraphic(upArrow);
            addButton.getStyleClass().add("button-flat");
            addButton.setOnAction(event -> {
                UpNext main = UpNext.getInstance();
                Map<String, Object> data = new HashMap<>();
                data.put("song-id", songID);
                data.put("party-id", UpNext.getInstance().getPartyID());
                try {
                    main.sendMessage("add-song", data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            this.getChildren().addAll(infoContainer, addButton);
            this.getStyleClass().add("artwork-padding");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UpNext main = UpNext.getInstance();
        main.on("search-results", (message) -> {
            SearchResultObject data = (SearchResultObject) message.getData().getData();
            PagingObject<TrackObject> tracks = data.tracks;
            ArrayList<TrackObject> items = tracks.items;
            Platform.runLater(() -> {
                searchResults.getChildren().clear();
                for (TrackObject item : items) {
                    searchResults.getChildren().add(new HBoxCell(
                            item.name,
                            item.artists.get(0).name,
                            item.album.images.stream().filter(e -> e.width < 100).collect(Collectors.toList()).get(0).url,
                            item.id
                    ));
                }
            });
            return null;
        });
    }

    public void doSearch(ActionEvent keyEvent) throws IOException {
        UpNext main = UpNext.getInstance();
        Map<String, Object> data = new HashMap<>();
        data.put("search-terms", searchTerms.getText());
        data.put("party-id", UpNext.getInstance().getPartyID());
        main.sendMessage("search", data);
    }
}
