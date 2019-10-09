package ca.ethanelliott.upnext.server.spotify.types;

import java.io.Serializable;

public class TuneableTrackObject implements Serializable {
    public TuneableTrackObject() { }
    public double acousticness;
    public double danceability;
    public int duration_ms;
    public double energy;
    public double instrumentalness;
    public int key;
    public double liveness;
    public float loudness;
    public int mode;
    public double popularity;
    public double speechiness;
    public double tempo;
    public int time_signature;
    public double valence;
}
