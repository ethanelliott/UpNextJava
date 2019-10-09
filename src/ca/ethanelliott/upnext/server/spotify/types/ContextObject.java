package ca.ethanelliott.upnext.server.spotify.types;

import java.io.Serializable;
import java.util.Map;

public class ContextObject implements Serializable {
    public ContextObject() {}
    public String type;
    public String href;
    public Map<String, String> external_urls;
    public String uri;
}
