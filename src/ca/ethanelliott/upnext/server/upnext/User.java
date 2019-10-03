package ca.ethanelliott.upnext.server.upnext;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String nickName;
    private int score;

    User() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
