package edu.uchicago.gerber._04interfaces.E9_13;

import java.awt.*;

public class Driver {
    public static void main(String[] args) {
        BetterRectangle rec = new BetterRectangle(new Point(1,2), new Dimension(1, 2));

        System.out.println(rec.getPerimeter());

        System.out.println(rec.getArea());
    }
}
