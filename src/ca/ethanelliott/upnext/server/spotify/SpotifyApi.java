package ca.ethanelliott.upnext.server.spotify;

import ca.ethanelliott.upnext.server.spotify.apis.*;

public class SpotifyApi {
    private static SpotifyApi instance = null;
    public static SpotifyApi getInstance() {
        if (instance == null) {
            instance = new SpotifyApi();
        }
        return instance;
    }

    public AlbumsAPI albums;
    public ArtistsAPI artists;
    public BrowseAPI browse;
    public LibraryAPI library;
    public PlayerAPI player;
    public PlaylistAPI playlist;
    public SearchAPI search;
    public TracksAPI tracks;
    public UsersAPI users;

    public SpotifyApi() {
        this.albums = new AlbumsAPI();
        this.artists = new ArtistsAPI();
        this.browse = new BrowseAPI();
        this.library = new LibraryAPI();
        this.player = new PlayerAPI();
        this.playlist = new PlaylistAPI();
        this.search = new SearchAPI();
        this.tracks = new TracksAPI();
        this.users = new UsersAPI();
    }
}