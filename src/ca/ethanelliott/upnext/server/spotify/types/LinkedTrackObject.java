package ca.ethanelliott.upnext.server.spotify.types;

import java.io.Serializable;
import java.util.Map;

public class LinkedTrackObject implements Serializable {
    public LinkedTrackObject() {}
    public Map<String, String> external_urls;
    public String href;
    public String id;
    public String type;
    public String uri;
}
