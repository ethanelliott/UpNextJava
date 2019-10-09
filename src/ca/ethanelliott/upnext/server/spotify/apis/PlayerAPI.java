package ca.ethanelliott.upnext.server.spotify.apis;

import ca.ethanelliott.upnext.server.requests.HTTP_METHODS;
import ca.ethanelliott.upnext.server.spotify.WebApiRequestBuilder;
import ca.ethanelliott.upnext.server.spotify.types.CurrentlyPlayingObject;
import com.google.gson.Gson;

import java.util.HashMap;

public class PlayerAPI {
    public PlayerAPI() {
    }

    public CurrentlyPlayingObject getPlayingContext(String accessToken) {
        return new Gson().fromJson(WebApiRequestBuilder
                .make(accessToken)
                .withMethod(HTTP_METHODS.GET)
                .withPath("/v1/me/player")
                .build()
                .execute(), CurrentlyPlayingObject.class);

    }

    public void play(String accessToken) {
        WebApiRequestBuilder
                .make(accessToken)
                .withMethod(HTTP_METHODS.PUT)
                .withPath("/v1/me/player/play")
                .build()
                .execute();
    }

    public void pause(String accessToken) {
        WebApiRequestBuilder
                .make(accessToken)
                .withMethod(HTTP_METHODS.PUT)
                .withPath("/v1/me/player/pause")
                .build()
                .execute();
    }

    public void next(String accessToken) {
        WebApiRequestBuilder
                .make(accessToken)
                .withMethod(HTTP_METHODS.POST)
                .withPath("/v1/me/player/next")
                .build()
                .execute();
    }
}
