package edu.uchicago.gerber._04interfaces.E9_13;

import java.awt.*;

public class BetterRectangle extends Rectangle {
    public BetterRectangle (Point p, Dimension d) {
        super.setLocation(p);
        super.setSize(d);
    }

    public double getPerimeter() {
        double h = super.getHeight();
        double w = super.getWidth();
        return 2*(h+w);
    }

    public double getArea() {
        double h = super.getHeight();
        double w = super.getWidth();
        return h*w;
    }
}
