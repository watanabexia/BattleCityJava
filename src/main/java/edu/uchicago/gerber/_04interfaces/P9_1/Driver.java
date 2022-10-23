package edu.uchicago.gerber._04interfaces.P9_1;

public class Driver {
    public static void main(String[] args) {
        Clock c = new Clock();

        System.out.println(c.getHours());

        System.out.println(c.getMinutes());

        System.out.println(c.getTime());

        WorldClock wc = new WorldClock(3);

        System.out.println(wc.getHours());

        System.out.println(wc.getMinutes());

        System.out.println(wc.getTime());
    }
}
