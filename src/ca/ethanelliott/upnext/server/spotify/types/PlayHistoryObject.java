package ca.ethanelliott.upnext.server.spotify.types;

import java.io.Serializable;

public class PlayHistoryObject implements Serializable {
    public PlayHistoryObject() {}
    public SimplifiedTrackObject track;
    public Object played_at;
    public ContextObject context;
}
