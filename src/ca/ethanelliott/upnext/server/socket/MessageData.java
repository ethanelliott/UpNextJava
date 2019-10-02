package ca.ethanelliott.upnext.server.socket;

import java.io.Serializable;

public class MessageData implements Serializable {
    private Object data;
    private String eventIdentifier;
    MessageData(String eventIdentifier, Object data) {
        this.data = data;
        this.eventIdentifier = eventIdentifier;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getEventIdentifier() {
        return eventIdentifier;
    }

    public void setEventIdentifier(String eventIdentifier) {
        this.eventIdentifier = eventIdentifier;
    }
}