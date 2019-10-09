package ca.ethanelliott.upnext.server.spotify.types;

import java.io.Serializable;

public class RecommendationSeedObject implements Serializable {
    public RecommendationSeedObject() { }
    public int afterFilteringSize;
    public int afterRelinkingSize;
    public String href;
    public String id;
    public int initialPoolSize;
    public String type;
}
