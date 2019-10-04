package ca.ethanelliott.upnext.server.spotify.apis;

import ca.ethanelliott.upnext.server.requests.HTTP_METHODS;
import ca.ethanelliott.upnext.server.spotify.WebApiRequestBuilder;

import java.util.Map;

public class TracksAPI {
    public TracksAPI() {
    }
    public Map<String, Object> getTrack(String accessToken, String trackID) {
        return WebApiRequestBuilder
                .make(accessToken)
                .withMethod(HTTP_METHODS.GET)
                .withPath(String.format("/v1/tracks/%s", trackID))
                .build()
                .execute();
    }
}
