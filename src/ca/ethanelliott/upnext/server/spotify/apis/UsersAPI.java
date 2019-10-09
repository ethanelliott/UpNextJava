package ca.ethanelliott.upnext.server.spotify.apis;

import ca.ethanelliott.upnext.server.requests.HTTP_METHODS;
import ca.ethanelliott.upnext.server.spotify.WebApiRequestBuilder;
import ca.ethanelliott.upnext.server.spotify.types.PrivateUserObject;
import com.google.gson.Gson;

import java.util.Map;

public class UsersAPI {
    public UsersAPI() {
    }

    public PrivateUserObject getCurrentUsersProfile(String accessToken) {
        return new Gson().fromJson(WebApiRequestBuilder
                .make(accessToken)
                .withMethod(HTTP_METHODS.GET)
                .withPath("/v1/me")
                .build()
                .execute(), PrivateUserObject.class);
    }

}
