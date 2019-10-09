package ca.ethanelliott.upnext.server.spotify.types;

import java.io.Serializable;

public class AudioFeaturesObject implements Serializable {
    public AudioFeaturesObject() {}
    public double acousticness;
    public String analysis_url;
    public double danceability;
    public int duration_ms;
    public double energy;
    public String id;
    public double instrumentalness;
    public int key;
    public double liveness;
    public float loudness;
    public int mode;
    public double speechiness;
    public double tempo;
    public int time_signature;
    public String track_ref;
    public String type;
    public String uri;
    public double valence;
}
