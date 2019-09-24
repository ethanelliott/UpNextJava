package ca.ethanelliott.upnext.upnext;

public class PlaylistAdded {
    private String uuid;
    private String name;
    private double time;

    PlaylistAdded () { }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }
}
