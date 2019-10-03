package ca.ethanelliott.upnext.server.spotify;

public class WebApiRequestBuilder {
    private static final String DEFAULT_HOST = "api.spotify.com";
    private static final String DEFAULT_SCHEME = "https";
    WebApiRequestBuilder() { }

    public static SpotifyRequestBuilder make(String token) {
        return SpotifyRequestBuilder
                .builder()
                .withHost(DEFAULT_HOST)
                .withScheme(DEFAULT_SCHEME)
                .withAuth(token);
    }
}
