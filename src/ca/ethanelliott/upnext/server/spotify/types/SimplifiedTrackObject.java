package ca.ethanelliott.upnext.server.spotify.types;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class SimplifiedTrackObject implements Serializable {
    public SimplifiedTrackObject() { }
    public ArrayList<SimplifiedArtistObject> artists;
    public ArrayList<String> available_markets;
    public int disc_number;
    public int duration_ms;
    public boolean explicit;
    public Map<String, String> external_urls;
    public String href;
    public String id;
    public boolean is_playable;
    public LinkedTrackObject linked_from;
    public String name;
    public String preview_url;
    public int track_number;
    public String type;
    public String uri;
}
