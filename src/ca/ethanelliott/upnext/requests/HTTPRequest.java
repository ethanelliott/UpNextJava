package ca.ethanelliott.upnext.requests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class HTTPRequest {
    private URL url;
    private HTTP_METHODS method;

    HTTPRequest(HTTPRequestBuilder builder) {
        this.url = builder.url;
        this.method = builder.method;
    }

    public Response makeRequest() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) this.url.openConnection();
        try {
            connection.setRequestMethod(this.method.toString());
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        int responseCode;
        BufferedReader in;
        String inputLine;
        StringBuilder response = new StringBuilder();
        responseCode = connection.getResponseCode();
        in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return new Response(responseCode, response.toString());
    }
}
