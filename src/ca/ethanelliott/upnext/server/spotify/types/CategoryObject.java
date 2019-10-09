package ca.ethanelliott.upnext.server.spotify.types;

import java.io.Serializable;
import java.util.ArrayList;

public class CategoryObject implements Serializable {
    public CategoryObject() {}
    public String href;
    public ArrayList<ImageObject> icons;
    public String id;
    public String name;
}
