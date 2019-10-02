package ca.ethanelliott.upnext.server.spotify;

import ca.ethanelliott.upnext.server.spotify.apis.*;

public class SpotifyApi {
    private Credentials credentials;

    public AlbumsAPI albums;
    public ArtistsAPI artists;
    public BrowseAPI browse;
    public LibraryAPI library;
    public PlayerAPI player;
    public PlaylistAPI playlist;
    public SearchAPI search;
    public TracksAPI tracks;

    public SpotifyApi(Credentials credentials) {
        this.credentials = credentials;
        this.albums = new AlbumsAPI(this.credentials);
        this.artists = new ArtistsAPI(this.credentials);
        this.browse = new BrowseAPI(this.credentials);
        this.library = new LibraryAPI(this.credentials);
        this.player = new PlayerAPI(this.credentials);
        this.playlist = new PlaylistAPI(this.credentials);
        this.search = new SearchAPI(this.credentials);
        this.tracks = new TracksAPI(this.credentials);
    }
}