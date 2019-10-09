package ca.ethanelliott.upnext.server.spotify.types;

import java.io.Serializable;
import java.util.ArrayList;

public class AlbumObject implements Serializable {
    public AlbumObject() { }
    public String album_type;
    public ArrayList<ArtistObject> artists;
    public ArrayList<String> available_markets;
    public ArrayList<Object> copyrights; //CopyrightObject
    public ExternalIdObject external_ids;
    public Object external_urls; //Map<String, String>
    public ArrayList<String> genres;
    public String href;
    public String id;
    public ArrayList<Object> images; //ImageObject
    public String label;
    public String name;
    public int popularity;
    public String release_date;
    public String release_date_precision;
    public ArrayList<SimplifiedTrackObject> tracks;
    public String type;
    public String uri;
}
