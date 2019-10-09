package ca.ethanelliott.upnext.server.spotify.apis;

import ca.ethanelliott.upnext.server.requests.HTTP_METHODS;
import ca.ethanelliott.upnext.server.spotify.WebApiRequestBuilder;
import ca.ethanelliott.upnext.server.spotify.types.TrackObject;
import com.google.gson.Gson;

public class TracksAPI {
    public TracksAPI() {
    }

    public TrackObject getTrack(String accessToken, String trackID) {
        return new Gson().fromJson(WebApiRequestBuilder
                .make(accessToken)
                .withMethod(HTTP_METHODS.GET)
                .withPath(String.format("/v1/tracks/%s", trackID))
                .build()
                .execute().toString(), TrackObject.class);

    }
}
