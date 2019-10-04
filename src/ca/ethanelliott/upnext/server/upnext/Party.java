package ca.ethanelliott.upnext.server.upnext;

import java.io.Serializable;
import java.util.List;

public class Party implements Serializable {
    private String uuid;
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
    private List<PlaylistEntry> playlist;
    private String currentTrack;
    private boolean playState;
    private List<User> users;
    private List<String> voteSkipList;
    private List<String> history;

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

    public List<PlaylistEntry> getPlaylist() {
        return playlist;
    }

    public void setPlaylist(List<PlaylistEntry> playlist) {
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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<String> getVoteSkipList() {
        return voteSkipList;
    }

    public void setVoteSkipList(List<String> voteSkipList) {
        this.voteSkipList = voteSkipList;
    }

    public List<String> getHistory() {
        return history;
    }

    public void setHistory(List<String> history) {
        this.history = history;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}