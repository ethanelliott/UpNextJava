package ca.ethanelliott.upnext.server.spotify.types;

import java.io.Serializable;
import java.util.ArrayList;

public class ArtistObject implements Serializable {
    public ArtistObject() { }
    public Object external_urls; //Map<String, String>
    public Object followers; // FollowersObject
    public ArrayList<String> genres;
    public String href;
    public String id;
    public ArrayList<Object> images; // ImageObject
    public String name;
    public int popularity;
    public String type;
    public String uri;
}