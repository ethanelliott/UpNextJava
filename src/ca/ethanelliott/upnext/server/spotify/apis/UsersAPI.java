package ca.ethanelliott.upnext.server.spotify.apis;

import ca.ethanelliott.upnext.server.requests.HTTP_METHODS;
import ca.ethanelliott.upnext.server.spotify.WebApiRequestBuilder;

import java.util.Map;

public class UsersAPI {
    public UsersAPI() {
    }

    public Map<String, Object> getCurrentUsersProfile(String accessToken) {
        return WebApiRequestBuilder
                .make(accessToken)
                .withMethod(HTTP_METHODS.GET)
                .withPath("/v1/me")
                .build()
                .execute();
    }

}
