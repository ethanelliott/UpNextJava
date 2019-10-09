package ca.ethanelliott.upnext.server.spotify.types;

import java.io.Serializable;
import java.util.ArrayList;

public class DevicesObject implements Serializable {
    public DevicesObject() {}
    public ArrayList<DeviceObject> devices;
}
