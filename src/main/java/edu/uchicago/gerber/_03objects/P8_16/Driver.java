package edu.uchicago.gerber._03objects.P8_16;

public class Driver {
    public static void main(String[] args) {
        Mailbox mbox = new Mailbox();
        Message m1 = new Message("a", "b");
        m1.append("HAHAHAHA1");
        Message m2 = new Message("c", "d");
        m2.append("HAHAHAHA2");
        mbox.addMessage(m1);
        mbox.addMessage(m2);
        Message m = mbox.getMessage(1);
        System.out.println(m.toString());
        mbox.removeMessage(0);
        m = mbox.getMessage(0);
        System.out.println(m.toString());
    }
}
