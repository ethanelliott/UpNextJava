package ca.ethanelliott.upnext.server.spotify.types;

import java.io.Serializable;

public class CurrentlyPlayingObject implements Serializable {
    public CurrentlyPlayingObject() {}
    public DeviceObject device;
    public String repeat_state;
    public boolean shuffle_state;
    public ContextObject context;
    public long timestamp;
    public int progress_ms;
    public boolean is_playing;
    public TrackObject item;
    public String currently_playing_type;
    public Object actions;
}
