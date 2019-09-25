package ca.ethanelliott.upnext.socket;

public class Message {
    // Object used for data as a general placeholder
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
}