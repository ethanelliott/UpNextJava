package ca.ethanelliott.upnext.server.spotify.types;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class PlaylistObject implements Serializable {
    public PlaylistObject() {
    }

    public boolean collaborative;
    public Map<String, String> external_urls;
    public String href;
    public String id;
    public ArrayList<ImageObject> images;
    public String name;
    public PublicUserObject owner;
    @SerializedName("public")
    public boolean _public;
    public String snapshot_id;
    public ArrayList<PlaylistTrackObject> tracks;
    public String type;
    public String uri;
}
