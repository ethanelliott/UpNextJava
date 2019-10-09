package ca.ethanelliott.upnext.server.spotify.types;

import java.io.Serializable;

public class PlaylistTrackObject implements Serializable {
    public PlaylistTrackObject() { }
    public Object added_at;
    public PublicUserObject added_by;
    public boolean is_local;
    public TrackObject track;
}
