package ca.ethanelliott.upnext.server.spotify.types;

import java.io.Serializable;

public class PlayerErrorObject implements Serializable {
    public PlayerErrorObject() {}
    public int status;
    public String message;
    public String reason;
}
