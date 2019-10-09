package ca.ethanelliott.upnext.server.spotify.types;

import java.io.Serializable;
import java.util.ArrayList;

public class PagingObject<T> implements Serializable {
    public PagingObject() {}
    public ArrayList<T> items;
    public String href;
    public int limit;
    public String next;
    public int offset;
    public String previous;
    public int total;
}
