package ca.ethanelliott.upnext.upnext;

import java.util.ArrayList;

public class Party {
    private String name;
    private String code;
    private double start;
    private String backgroundColour;
    private String progressColour;
    private String adminPassword;
    private String token;
    private String refreshToken;
    private double tokenExpiry;
    private String userID;
    private String playlistID;
    private ArrayList<PlaylistEntry> playlist;
    private String currentTrack;
    private boolean playState;
    private ArrayList<User> users;
    private ArrayList<String> voteSkipList;
    private ArrayList<String> history;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getStart() {
        return start;
    }

    public void setStart(double start) {
        this.start = start;
    }

    public String getBackgroundColour() {
        return backgroundColour;
    }

    public void setBackgroundColour(String backgroundColour) {
        this.backgroundColour = backgroundColour;
    }

    public String getProgressColour() {
        return progressColour;
    }

    public void setProgressColour(String progressColour) {
        this.progressColour = progressColour;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public double getTokenExpiry() {
        return tokenExpiry;
    }

    public void setTokenExpiry(double tokenExpiry) {
        this.tokenExpiry = tokenExpiry;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPlaylistID() {
        return playlistID;
    }

    public void setPlaylistID(String playlistID) {
        this.playlistID = playlistID;
    }

    public ArrayList<PlaylistEntry> getPlaylist() {
        return playlist;
    }

    public void setPlaylist(ArrayList<PlaylistEntry> playlist) {
        this.playlist = playlist;
    }

    public String getCurrentTrack() {
        return currentTrack;
    }

    public void setCurrentTrack(String currentTrack) {
        this.currentTrack = currentTrack;
    }

    public boolean isPlayState() {
        return playState;
    }

    public void setPlayState(boolean playState) {
        this.playState = playState;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<String> getVoteSkipList() {
        return voteSkipList;
    }

    public void setVoteSkipList(ArrayList<String> voteSkipList) {
        this.voteSkipList = voteSkipList;
    }

    public ArrayList<String> getHistory() {
        return history;
    }

    public void setHistory(ArrayList<String> history) {
        this.history = history;
    }
}
