package ca.ethanelliott.upnext.server.spotify.types;

import com.google.gson.Gson;

import java.io.Serializable;

public class SearchResultObject implements Serializable {
    public SearchResultObject() { }

    public PagingObject<ArtistObject> artists;
    public PagingObject<SimplifiedAlbumObject> albums;
    public PagingObject<TrackObject> tracks;
    public PagingObject<SimplifiedPlaylistObject> playlists;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
