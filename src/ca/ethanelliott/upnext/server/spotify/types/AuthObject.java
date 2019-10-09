package ca.ethanelliott.upnext.server.spotify.types;

import java.io.Serializable;

public class AuthObject implements Serializable {
    public AuthObject() { }
    public String scope;
    public String token_type;
    public double expires_in;
    public String refresh_token;
    public String access_token;
}
