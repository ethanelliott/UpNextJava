package ca.ethanelliott.upnext.server.requests;

import com.google.gson.Gson;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import javax.lang.model.type.UnknownTypeException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HTTPRequest {
    public static String makeRequest(HTTP_METHODS method, URL url, Map<String, String> headers, Map<String, String> queryParameters, Map<String, String> bodyParameters) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        if (!queryParameters.isEmpty()) {
            StringBuilder queryString = new StringBuilder(url.toString());
            queryString.append("?");
            for (Map.Entry<String, String> queryParam : queryParameters.entrySet()) {
                queryString.append(String.format("%s=%s&", queryParam.getKey(), queryParam.getValue()));
            }
            String newUrl = queryString.toString();
            if (newUrl.charAt(newUrl.length() - 1) == '&') {
                newUrl = newUrl.substring(0, newUrl.length() - 1);
            }
            url = new URL(newUrl);
        }
        switch (method) {
            case GET:
                // DOING THE GET
                HttpGet get = new HttpGet(url.toString());
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    get.addHeader(header.getKey(), header.getValue());
                }
                response = httpClient.execute(get);
                break;
            case POST:
                // DOING THE POST
                HttpPost post = new HttpPost(url.toString());
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    post.addHeader(header.getKey(), header.getValue());
                }
                List<NameValuePair> postParams = new ArrayList<>();
                for (Map.Entry<String, String> param : bodyParameters.entrySet()) {
                    postParams.add(new BasicNameValuePair(param.getKey(), param.getValue()));
                }
                post.setEntity(new UrlEncodedFormEntity(postParams));
                response = httpClient.execute(post);
                break;
            case PUT:
                // DOING THE PUT
                HttpPut put = new HttpPut(url.toString());
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    put.addHeader(header.getKey(), header.getValue());
                }
                List<NameValuePair> putParams = new ArrayList<>();
                for (Map.Entry<String, String> param : bodyParameters.entrySet()) {
                    putParams.add(new BasicNameValuePair(param.getKey(), param.getValue()));
                }
                put.setEntity(new UrlEncodedFormEntity(putParams));
                response = httpClient.execute(put);
                break;
            case DELETE:
                HttpDelete delete = new HttpDelete(url.toString());
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    delete.addHeader(header.getKey(), header.getValue());
                }
                response = httpClient.execute(delete);
                break;
            default:
                throw new Exception("Cannot find the specified method: " + method);
        }
        if (response.getEntity() != null) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {
                String rlString = "";
                StringBuilder resJSON = new StringBuilder();
                while ((rlString = br.readLine()) != null) {
                    resJSON.append(rlString);
                }
                Gson g = new Gson();
                return resJSON.toString();
            }
        }
        httpClient.close();
        return null;
    }
}
