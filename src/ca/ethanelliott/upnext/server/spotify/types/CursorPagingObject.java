package ca.ethanelliott.upnext.server.spotify.types;

import java.io.Serializable;
import java.util.ArrayList;

public class CursorPagingObject implements Serializable {
    public CursorPagingObject() {}
    public String href;
    public ArrayList<Object> items;
    public int limit;
    public String next;
    public CursorObject cursors;
    public int total;
}
