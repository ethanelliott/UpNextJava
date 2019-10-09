package ca.ethanelliott.upnext.server.spotify;

import ca.ethanelliott.upnext.server.requests.HTTPRequest;
import ca.ethanelliott.upnext.server.requests.HTTP_METHODS;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SpotifyRequest {
    HTTP_METHODS method;
    String host;
    String scheme;
    String path;
    Map<String, String> headers;
    Map<String, String> queryParameters;
    Map<String, String> bodyParameters;

    public SpotifyRequest(SpotifyRequestBuilder builder) {
        this.method = builder.method;
        this.host = builder.host;
        this.scheme = builder.scheme;
        this.path = builder.path;
        this.headers = builder.headers;
        this.queryParameters = builder.queryParameters;
        this.bodyParameters = builder.bodyParameters;
    }

    public String execute() {
        try {
            return HTTPRequest.makeRequest(this.method, this.getURI(), this.headers, this.queryParameters, this.bodyParameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private URL getURI() {
        try {
            return new URL(String.format("%s://%s%s", this.scheme, this.host, this.path));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
