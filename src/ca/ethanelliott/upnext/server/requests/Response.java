package ca.ethanelliott.upnext.server.requests;

import com.google.gson.Gson;

public class Response {
    private int responseCode;
    private String data;

    public Response(int responseCode, String data) {
        this.responseCode = responseCode;
        this.data = data;
    }

    public Object getObject(Class objectType) {
        return new Gson().fromJson(this.getData(), objectType);
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getData() {
        return data;
    }
}
