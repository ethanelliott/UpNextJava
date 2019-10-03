package ca.ethanelliott.upnext.server.spotify;

public class CredentialsBuilder {
    // TEMP CONSTANTS NEED TO GO INTO A RESOURCE FILE
    final String SPOTIFY_CLIENT_ID = "dd8b5386683d47cc9d955a00c1a9c3f8";
    final String SPOTIFY_CLIENT_SECRET = "8de6722b006047c7b2bbb9e1de194f24";

    private String clientID = SPOTIFY_CLIENT_ID;
    private String clientSecret = SPOTIFY_CLIENT_SECRET;
    private String accessToken;
    private String refreshToken;
    private String redirectURI;

    public static CredentialsBuilder builder() {
        return new CredentialsBuilder();
    }

    public Credentials build() {
        return new Credentials(
                this.clientID,
                this.clientSecret,
                this.accessToken,
                this.refreshToken,
                this.redirectURI
        );
    }

//    public CredentialsBuilder setClientID(String clientID) {
//        this.clientID = clientID;
//        return this;
//    }
//
//    public CredentialsBuilder setClientSecret(String clientSecret) {
//        this.clientSecret = clientSecret;
//        return this;
//    }

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