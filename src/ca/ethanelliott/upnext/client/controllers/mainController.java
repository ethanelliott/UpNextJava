package ca.ethanelliott.upnext.client.controllers;

import ca.ethanelliott.upnext.client.UpNext;
import ca.ethanelliott.upnext.server.socket.Message;
import ca.ethanelliott.upnext.server.upnext.Party;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class mainController implements Initializable {
    @FXML
    public Label songName;
    @FXML
    public Label songArtist;
    @FXML
    public Label partyCode;
    @FXML
    public ImageView albumArtwork;
    @FXML
    public ProgressBar progress;
    @FXML
    public ImageView trackPlayState;
    @FXML
    private URL location;

    private boolean isPlaying = false;

    public mainController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UpNext main = UpNext.getInstance();
        main.on("event-loop-response", (Message message) -> {
            HashMap<String, Object> data = (HashMap<String, Object>) message.getData().getData();
            Party party = (Party) data.get("party");
            HashMap<String, Object> playerState = (HashMap<String, Object>) data.get("player");
            Platform.runLater(() -> {
                HashMap<String, Object> item = (HashMap<String, Object>)playerState.get("item");
                HashMap<String, Object> artist = (HashMap<String, Object>) ((ArrayList<Object>) item.get("artists")).get(0);
                HashMap<String, Object> album = (HashMap<String, Object>) ((ArrayList<Object>) ((HashMap<String, Object>) item.get("album")).get("images")).get(0);
                partyCode.setText(party.getCode());
                songName.setText((String) item.get("name"));
                songArtist.setText((String) artist.get("name"));
                albumArtwork.setImage(new Image((String) album.get("url")));
                double track_time = ((double) playerState.get("progress_ms")) / (double)(item.get("duration_ms"));
                progress.setProgress(track_time);
                isPlaying = (boolean) playerState.get("is_playing");
                if (isPlaying) {
                    trackPlayState.setImage(new Image(String.valueOf(this.getClass().getClassLoader().getResource("ca/ethanelliott/upnext/resources/pause.png"))));
                } else {
                    trackPlayState.setImage(new Image(String.valueOf(this.getClass().getClassLoader().getResource("ca/ethanelliott/upnext/resources/play.png"))));
                }
            });
            return null;
        });
        main.startEventLoop();
    }

    @FXML
    public void openQueue(javafx.event.ActionEvent event) throws IOException {
        Parent view = FXMLLoader.load(Objects.requireNonNull
                (getClass()
                        .getClassLoader()
                        .getResource("ca/ethanelliott/upnext/client/interfaces/queue.fxml")
                )
        );
        Stage queue = new Stage();
        Scene scene = new Scene(view);
        scene.getStylesheets().add(String.valueOf(this.getClass().getClassLoader().getResource("ca/ethanelliott/upnext/client/interfaces/style/style.css")));
        queue.initModality(Modality.WINDOW_MODAL);
        queue.initStyle(StageStyle.UNIFIED);
        queue.setTitle("UpNext Queue");
        queue.getIcons().add(new Image(String.valueOf(this.getClass().getClassLoader().getResource("ca/ethanelliott/upnext/resources/music_note.png"))));
        queue.setScene(scene);
        queue.show();
    }

    @FXML
    public void togglePlayState(ActionEvent event) throws IOException {
        Map<String, Object> data = new HashMap<>();
        data.put("is-playing", isPlaying);
        data.put("party-id", UpNext.getInstance().getPartyID());
        UpNext.getInstance().sendMessage("toggle-playback", data);
    }

    @FXML
    public void skipSong(ActionEvent event) throws IOException {
        UpNext.getInstance().sendMessage("skip-song", UpNext.getInstance().getPartyID());
    }
}
