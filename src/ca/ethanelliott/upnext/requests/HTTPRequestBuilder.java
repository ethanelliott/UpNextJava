package ca.ethanelliott.upnext.requests;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class HTTPRequestBuilder {
    URL url;
    String host;
    String scheme;
    ArrayList<String> queryParameters;
    ArrayList<String> bodyParameters;
    ArrayList<String> headers;
    String path;
    HTTP_METHODS method;

    private HTTPRequestBuilder() { }
    static public HTTPRequestBuilder make() {
        return new HTTPRequestBuilder();
    }

    public HTTPRequestBuilder withMethod(HTTP_METHODS method) {
        this.method = method;
        return this;
    }

    public HTTPRequestBuilder withHost(String host) {
        this.host = host;
        return this;
    }

    public HTTPRequestBuilder withScheme(String scheme) {
        this.scheme = scheme;
        return this;
    }

    public HTTPRequestBuilder withPath(String path) {
        this.path = path;
        return this;
    }

    public HTTPRequestBuilder withQueryParameters(ArrayList<String> queryParameters) {
        this.queryParameters = queryParameters;
        return this;
    }

    public HTTPRequestBuilder withBodyParameters(ArrayList<String> bodyParameters) {
        this.bodyParameters = bodyParameters;
        return this;
    }

    public HTTPRequestBuilder withHeaders(ArrayList<String> headers) {
        this.headers = headers;
        return this;
    }

    public HTTPRequestBuilder withAuth(String accessToken) {
        this.headers.add(String.format("Authorization: Bearer %s", accessToken));
        return this;
    }

    public HTTPRequest build() {
        String urlString = String.format("%s://%s", this.scheme, this.host);
        try {
            this.url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return new HTTPRequest(this);
    }

}