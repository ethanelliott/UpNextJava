package ca.ethanelliott.upnext.server.spotify.types;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class PrivateUserObject implements Serializable {
    public PrivateUserObject() { }
    public String country;
    public String display_name;
    public String email;
    public Map<String, String> external_urls;
    public FollowersObject followers;
    public String href;
    public String id;
    public ArrayList<ImageObject> images;
    public String product;
    public String type;
    public String uri;
}
