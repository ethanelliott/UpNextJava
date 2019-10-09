package ca.ethanelliott.upnext.server.spotify.types;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class PublicUserObject implements Serializable {
    public PublicUserObject() { }
    public String display_name;
    public Map<String, String> external_urls;
    public FollowersObject followers;
    public String href;
    public String id;
    public ArrayList<ImageObject> images;
    public String type;
    public String uri;
}
