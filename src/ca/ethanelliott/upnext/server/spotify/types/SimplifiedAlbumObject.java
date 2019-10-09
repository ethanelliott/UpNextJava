package ca.ethanelliott.upnext.server.spotify.types;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class SimplifiedAlbumObject implements Serializable {
    public SimplifiedAlbumObject() { }
    public String album_group;
    public String album_type;
    public ArrayList<SimplifiedArtistObject> artists;
    public ArrayList<String> available_markets;
    public Map<String, String> external_urls;
    public String href;
    public String id;
    public ArrayList<ImageObject> images;
    public String name;
    public String type;
    public String uri;
}
