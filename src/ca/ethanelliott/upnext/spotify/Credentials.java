package ca.ethanelliott.upnext.spotify;

public class Credentials {
    private String clientID;
    private String clientSecret;
    private String accessToken;
    private String refreshToken;
    private String redirectURI;

    public Credentials() {
        this.clientID = "";
        this.clientSecret = "";
        this.accessToken = "";
        this.refreshToken = "";
        this.redirectURI = "";
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRedirectURI() {
        return redirectURI;
    }

    public void setRedirectURI(String redirectURI) {
        this.redirectURI = redirectURI;
    }
}
