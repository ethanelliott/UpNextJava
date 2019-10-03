package ca.ethanelliott.upnext.client.controllers;

import ca.ethanelliott.upnext.client.UpNext;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class spotifyAuthController implements Initializable {
    @FXML
    public Label textBox;
    @FXML
    public Button nextScene;
    @FXML
    private URL location;

    public spotifyAuthController() {
    }

    private Map<String, String> getSpotifyAuth() {
        final String clientID = "dd8b5386683d47cc9d955a00c1a9c3f8";
        final String scope = "user-read-private user-read-email user-library-read user-library-modify playlist-read-private streaming app-remote-control user-modify-playback-state user-read-currently-playing user-read-playback-state playlist-modify-public playlist-modify-private";

        try {
            String url = String.format(
                    "https://accounts.spotify.com/authorize?%s=%s&%s=%s&%s=%s&%s=%s&%s=%s",
                    "response_type", "code",
                    "client_id", clientID,
                    "scope", URLEncoder.encode(scope, StandardCharsets.UTF_8.toString()),
                    "redirect_uri", "http://localhost:1337/party/auth-callback",
                    "state", UUID.randomUUID()
            );
            Map<String, String> urlParameters = getURLParams(url);
            if (urlParameters != null) return urlParameters;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Map<String, String> getURLParams(String url) {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI(url));
                ServerSocket serverSocket = new ServerSocket(1337);
                Socket clientSocket = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                Map<String, String> urlParameters = Arrays
                        .stream(in.readLine().split(" ")[1].split("/party/auth-callback\\?")[1].split("&"))
                        .map(e -> e.split("="))
                        .collect(Collectors.toMap(e -> e[0], e -> e[1]));
                printDonePage(clientSocket);
                clientSocket.close();
                return urlParameters;
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private void printDonePage(Socket clientSocket) throws IOException {
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
        String response = "<h1>YOU CAN CLOSE THIS TAB NOW :)</h1>";
        out.println("HTTP/1.1 200 OK");
        out.println("Content-Type: text/html");
        out.println("Content-Length: " + response.length());
        out.println();
        out.println(response);
        out.flush();
        out.close();
    }


    private HashMap makeRequest(URL url, String code) throws IOException {
        final String SPOTIFY_CLIENT_ID = "dd8b5386683d47cc9d955a00c1a9c3f8";
        final String SPOTIFY_CLIENT_SECRET = "8de6722b006047c7b2bbb9e1de194f24";

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url.toString());
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("grant_type", "authorization_code"));
        params.add(new BasicNameValuePair("code", code));
        params.add(new BasicNameValuePair("redirect_uri", "http://localhost:1337/party/auth-callback"));
        httpPost.setEntity(new UrlEncodedFormEntity(params));

        /*
        THIS IS IMPORTANT
         */
        String authString = Base64.getEncoder().encodeToString(String.format("%s:%s", SPOTIFY_CLIENT_ID, SPOTIFY_CLIENT_SECRET).getBytes());
        httpPost.addHeader("Authorization", String.format("Basic %s", authString));

        CloseableHttpResponse response = httpClient.execute(httpPost);
        if (response.getEntity() != null) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {
                // do something useful
                String data = "";
                StringBuilder res = new StringBuilder();
                while ((data = br.readLine()) != null) { res.append(data); }
                Gson g = new Gson();
                return g.fromJson(res.toString(), HashMap.class);
            }
        }
        httpClient.close();
        return null;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.nextScene.setDisable(false);
    }

    public void nextWindow(ActionEvent event) {
        Map<String, String> response = this.getSpotifyAuth();
        assert response != null;
        HashMap tokens = new HashMap<>();
        if (response.get("code") != null) {
            try {
                tokens = makeRequest(new URL("https://accounts.spotify.com/api/token"), response.get("code"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            // this needs to connect to the server and then create the new party
            // HERE
            UpNext main = UpNext.getInstance();
            if (main.interSceneObject instanceof HashMap) {
                try {
                    String name = (String) ((HashMap) main.interSceneObject).get("name");
                    String password = (String) ((HashMap) main.interSceneObject).get("password");
                    Map<String, Object> data = new HashMap<>();
                    data.put("name", name);
                    data.put("password", password);
                    data.put("tokens", tokens);
                    main.sendMessage("create-party", data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Parent view = null;
        try {
            view = FXMLLoader.load(Objects.requireNonNull
                    (getClass()
                            .getClassLoader()
                            .getResource("ca/ethanelliott/upnext/client/interfaces/main.fxml")
                    )
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert view != null;
        Scene scene = new Scene(view);
        scene.getStylesheets().add(String.valueOf(this.getClass().getClassLoader().getResource("ca/ethanelliott/upnext/client/interfaces/style/style.css")));
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
}