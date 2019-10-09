package ca.ethanelliott.upnext.server.spotify.types;

import java.io.Serializable;
import java.util.ArrayList;

public class RecommendationsResponseObject implements Serializable {
    public RecommendationsResponseObject() { }
    public ArrayList<RecommendationSeedObject> seeds;
    public ArrayList<SimplifiedTrackObject> tracks;

}
