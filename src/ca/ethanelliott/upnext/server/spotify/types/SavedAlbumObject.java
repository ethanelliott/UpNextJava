package ca.ethanelliott.upnext.server.spotify.types;

import java.io.Serializable;

public class SavedAlbumObject implements Serializable {
    public SavedAlbumObject() { }
    public Object added_at;
    public AlbumObject album;
}
