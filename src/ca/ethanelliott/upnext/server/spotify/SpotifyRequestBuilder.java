package ca.ethanelliott.upnext.server.spotify;

import ca.ethanelliott.upnext.server.requests.HTTP_METHODS;

import java.util.HashMap;
import java.util.Map;

public class SpotifyRequestBuilder {
    HTTP_METHODS method;
    String host;
    String scheme;
    String path;
    Map<String, String> headers;
    Map<String, String> queryParameters;
    Map<String, String> bodyParameters;

    public static SpotifyRequestBuilder builder() {
        return new SpotifyRequestBuilder();
    }

    private SpotifyRequestBuilder() {
        this.headers = new HashMap<>();
        this.queryParameters = new HashMap<>();
        this.bodyParameters = new HashMap<>();
    }

    public SpotifyRequestBuilder withMethod(HTTP_METHODS method) {
        this.method = method;
        return this;
    }

    public SpotifyRequestBuilder withHost(String host) {
        this.host = host;
        return this;
    }

    public SpotifyRequestBuilder withScheme(String scheme) {
        this.scheme = scheme;
        return this;
    }

    public SpotifyRequestBuilder withPath(String path) {
        this.path = path;
        return this;
    }

    public SpotifyRequestBuilder withQueryParameters(Map<String, String> queryParameters) {
        this.queryParameters = queryParameters;
        return this;
    }

    public SpotifyRequestBuilder withBodyParameters(Map<String, String> bodyParameters) {
        this.bodyParameters = bodyParameters;
        return this;
    }

    public SpotifyRequestBuilder withHeaders(Map<String, String> headers) {
        this.headers.putAll(headers);
        return this;
    }

    public SpotifyRequestBuilder withAuth(String token) {
        this.headers.put("Authorization", String.format("Bearer %s", token));
        return this;
    }

    public SpotifyRequest build() {
        return new SpotifyRequest(this);
    }
}
