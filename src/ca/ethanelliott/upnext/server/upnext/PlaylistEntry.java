package ca.ethanelliott.upnext.server.upnext;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PlaylistEntry implements Serializable {
    private String id;
    private String name;
    private String artist;
    private String artwork;
    private int votes;
    private PlaylistAdded added;
    private List<User> upvoters;
    private List<User> downvoters;

    PlaylistEntry() { }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public PlaylistAdded getAdded() {
        return added;
    }

    public void setAdded(PlaylistAdded added) {
        this.added = added;
    }

    public List<User> getUpvoters() {
        return upvoters;
    }

    public void setUpvoters(List<User> upvoters) {
        this.upvoters = upvoters;
    }

    public List<User> getDownvoters() {
        return downvoters;
    }

    public void setDownvoters(List<User> downvoters) {
        this.downvoters = downvoters;
    }
}