package ca.ethanelliott.upnext.server.spotify.types;

import java.io.Serializable;
import java.util.Map;

public class SimplifiedArtistObject implements Serializable {
    public SimplifiedArtistObject() { }
    public Map<String, String> external_urls;
    public String href;
    public String id;
    public String name;
    public String type;
    public String uri;
}
