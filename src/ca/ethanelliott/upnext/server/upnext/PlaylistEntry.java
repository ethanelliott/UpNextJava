package ca.ethanelliott.upnext.server.upnext;

import java.io.Serializable;
import java.util.ArrayList;

public class PlaylistEntry implements Serializable {
    private int id;
    private String name;
    private String artist;
    private String artwork;
    private double votes;
    private PlaylistAdded added;
    private ArrayList<User> upvoters;
    private ArrayList<User> downvoters;

    PlaylistEntry() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getArtwork() {
        return artwork;
    }

    public void setArtwork(String artwork) {
        this.artwork = artwork;
    }

    public double getVotes() {
        return votes;
    }

    public void setVotes(double votes) {
        this.votes = votes;
    }

    public PlaylistAdded getAdded() {
        return added;
    }

    public void setAdded(PlaylistAdded added) {
        this.added = added;
    }

    public ArrayList<User> getUpvoters() {
        return upvoters;
    }

    public void setUpvoters(ArrayList<User> upvoters) {
        this.upvoters = upvoters;
    }

    public ArrayList<User> getDownvoters() {
        return downvoters;
    }

    public void setDownvoters(ArrayList<User> downvoters) {
        this.downvoters = downvoters;
    }
}