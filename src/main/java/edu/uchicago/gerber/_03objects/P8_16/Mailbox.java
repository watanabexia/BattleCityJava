package edu.uchicago.gerber._03objects.P8_16;

import java.util.ArrayList;

public class Mailbox {
    private final ArrayList<Message> messages;

    public Mailbox() {
        messages = new ArrayList<>();
    }

    public void addMessage(Message m) {
        messages.add(m);
    }

    public Message getMessage(int i) {
        return messages.get(i);
    }

    public void removeMessage(int i) {
        messages.remove(i);
    }
}
