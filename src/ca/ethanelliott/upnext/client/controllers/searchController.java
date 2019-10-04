package ca.ethanelliott.upnext.client.controllers;

import ca.ethanelliott.upnext.client.UpNext;
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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Function;

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
        Label label = new Label();
        Button addButton = new Button();

        HBoxCell(String songName, String songID) {
            super();

            label.setText(songName);
            label.setMaxWidth(Double.MAX_VALUE);
            label.getStyleClass().add("white-text");
            HBox.setHgrow(label, Priority.ALWAYS);
            this.setAlignment(Pos.CENTER_LEFT);

            ImageView upArrow = new ImageView(new Image(String.valueOf(this.getClass().getClassLoader().getResource("ca/ethanelliott/upnext/resources/add.png"))));
            upArrow.setFitHeight(16);
            upArrow.setPreserveRatio(true);
            addButton.setGraphic(upArrow);
            addButton.getStyleClass().add("button-outline");
            addButton.setOnAction(event -> {
                System.out.println(songID);
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
            this.getChildren().addAll(label, addButton);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UpNext main = UpNext.getInstance();
        main.on("search-results", (message) -> {
            HashMap<String, Object> data = (HashMap<String, Object>) message.getData().getData();
            HashMap<String, Object> tracks = (HashMap<String, Object>) data.get("tracks");
            ArrayList<Map<String, Object>> items = (ArrayList<Map<String, Object>>) tracks.get("items");
            Platform.runLater(() -> {
                searchResults.getChildren().clear();
                for (Map<String, Object> item : items) {
                    searchResults.getChildren().add(new HBoxCell((String) item.get("name"), (String) item.get("id")));
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
