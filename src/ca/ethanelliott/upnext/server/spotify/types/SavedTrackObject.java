package ca.ethanelliott.upnext.server.spotify.types;

import java.io.Serializable;

public class SavedTrackObject implements Serializable {
    public SavedTrackObject() { }
    public Object added_at;
    public TrackObject track;
}
