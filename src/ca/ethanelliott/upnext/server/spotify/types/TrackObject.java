package ca.ethanelliott.upnext.server.spotify.types;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class TrackObject implements Serializable {
    public TrackObject() { }
    public SimplifiedAlbumObject album;
    public ArrayList<ArtistObject> artists;
    public ArrayList<String> available_markets;
    public int disc_number;
    public int duration_ms;
    public boolean explicit;
    public ExternalIdObject external_ids;
    public Map<String, String> external_urls;
    public String href;
    public String id;
    public boolean is_playable;
    public TrackObject linked_from;
    public Object restrictions;
    public String name;
    public int popularity;
    public String preview_url;
    public int track_number;
    public String type;
    public String uri;
}
