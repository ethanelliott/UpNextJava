package ca.ethanelliott.upnext.socket;

import java.io.Serializable;

public class Message implements Serializable {
    private String sourceAddress;
    private String destinationAddress;
    private MessageData data;

    public Message(String source, String destination, String eventIdentifier, Object data) {
        this.sourceAddress = source;
        this.destinationAddress = destination;
        this.data = new MessageData(eventIdentifier, data);
    }

    public String getSourceAddress() {
        return sourceAddress;
    }

    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public MessageData getData() {
        return data;
    }

    public void setData(String eventIdentifier, Object data) {
        this.data = new MessageData(eventIdentifier, data);
    }

    @Override
    public String toString() {
        return "\nMessage:\n\tFrom: " +
                this.destinationAddress +
                "\n\tTo: " +
                this.sourceAddress +
                "\n\tData: \n\t\tEventID: " +
                this.data.getEventIdentifier() +
                "\n\t\t Data: " +
                this.data.getData().toString();
    }
}