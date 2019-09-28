package ca.ethanelliott.upnext.spotify;

public class CredentialsBuilder {
    private String clientID;
    private String clientSecret;
    private String accessToken;
    private String refreshToken;
    private String redirectURI;

    public static CredentialsBuilder builder() {
        return new CredentialsBuilder();
    }

    public Credentials build() {
        return new Credentials(this.clientID, this.clientSecret, this.accessToken, this.refreshToken, this.redirectURI);
    }

    public CredentialsBuilder setClientID(String clientID) {
        this.clientID = clientID;
        return this;
    }

    public CredentialsBuilder setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    public CredentialsBuilder setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public CredentialsBuilder setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    public CredentialsBuilder setRedirectURI(String redirectURI) {
        this.redirectURI = redirectURI;
        return this;
    }


}
