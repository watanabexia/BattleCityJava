package edu.uchicago.gerber._05dice.P10_10;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class RingComponent extends JComponent {

    private Shape createRing(int x, int y, int radius_out, int radius_in) {
        Ellipse2D out = new Ellipse2D.Double(x - radius_out, y - radius_out, 2*radius_out, 2*radius_out);
        Ellipse2D in = new Ellipse2D.Double(x - radius_in, y - radius_in, 2*radius_in, 2*radius_in);
        Area ring = new Area(out);
        ring.subtract(new Area(in));
        return ring;
    }

    private void drawRing(Graphics g, Color color, int x, int y) {
        int radius_out = 50;
        int radius_in = 45;
        Shape ring = createRing(x, y, radius_out, radius_in);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(color);
        g2d.fill(ring);
    }

    public void paintComponent(Graphics g) {
        drawRing(g, Color.BLUE, 200, 200);
        drawRing(g, Color.BLACK, 300, 200);
        drawRing(g, Color.RED, 400, 200);
        drawRing(g, Color.YELLOW, 250, 250);
        drawRing(g, Color.GREEN, 350, 250);
    }
}
