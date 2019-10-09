package ca.ethanelliott.upnext.server.spotify.apis;

import ca.ethanelliott.upnext.server.requests.HTTP_METHODS;
import ca.ethanelliott.upnext.server.spotify.WebApiRequestBuilder;
import ca.ethanelliott.upnext.server.spotify.types.SearchResultObject;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class SearchAPI {
    public SearchAPI() {
    }

    public SearchResultObject searchTracks(String accessToken, String searchTerm) {
        return getSearchResultObject(accessToken, searchTerm, "track");
    }

    public SearchResultObject searchAlbums(String accessToken, String searchTerm) {
        return getSearchResultObject(accessToken, searchTerm, "albums");
    }

    public SearchResultObject searchArtists(String accessToken, String searchTerm) {
        return getSearchResultObject(accessToken, searchTerm, "artists");
    }

    public SearchResultObject searchPlaylists(String accessToken, String searchTerm) {
        return getSearchResultObject(accessToken, searchTerm, "playlists");
    }

    public SearchResultObject searchEverything(String accessToken, String searchTerm) {
        return getSearchResultObject(accessToken, searchTerm, "track,albums,playlists,artists");
    }

    private SearchResultObject getSearchResultObject(String accessToken, String searchTerm, String type) {
        Map<String, String> qp = new HashMap<>();
        qp.put("q", searchTerm);
        qp.put("type", type);
        qp.put("market", "CA");
        qp.put("limit", "50");
        qp.put("offset", "0");
        return new Gson().fromJson(WebApiRequestBuilder
                .make(accessToken)
                .withMethod(HTTP_METHODS.GET)
                .withPath("/v1/search")
                .withQueryParameters(qp)
                .build()
                .execute(), SearchResultObject.class);
    }
}
