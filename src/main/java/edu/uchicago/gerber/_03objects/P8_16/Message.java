package edu.uchicago.gerber._03objects.P8_16;

public class Message {
    private final String recipient;
    private final String sender;
    private String message;

    public Message(String sender, String recipient) {
        this.sender = sender;
        this.recipient = recipient;
        this.message = "";
    }

    public void append(String text) {
        this.message += text + "\n";
    }

    public String toString() {
        return "From: "+this.sender+"\nTo: "+this.recipient+"\n"+this.message;
    }
}
