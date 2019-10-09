package ca.ethanelliott.upnext.server.spotify.types;

import java.io.Serializable;

public class ErrorObject implements Serializable {
    public ErrorObject() {}
    public int status;
    public String message;
}
