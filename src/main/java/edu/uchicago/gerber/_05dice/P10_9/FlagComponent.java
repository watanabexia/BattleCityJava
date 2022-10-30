package edu.uchicago.gerber._05dice.P10_9;

import javax.swing.*;
import java.awt.*;

public class FlagComponent extends JComponent {

    private void drawFlag(Graphics g, Color color_up, Color color_mid, Color color_down, int xLeft, int yTop, int width) {
        int height = width*2/3;
        int subheight = height/3;

        g.setColor(color_up);
        g.fillRect(xLeft, yTop, width, subheight);
        g.setColor(color_mid);
        g.fillRect(xLeft, yTop +subheight, width, subheight);
        g.setColor(color_down);
        g.fillRect(xLeft, yTop + 2*subheight, width, subheight);

        g.setColor(Color.BLACK);
        g.drawLine(xLeft, yTop, xLeft, yTop + height);
        g.drawLine(xLeft, yTop + height, xLeft + width, yTop + height);
        g.drawLine(xLeft + width, yTop + height, xLeft + width, yTop);
        g.drawLine(xLeft + width, yTop, xLeft, yTop);
    }

    public void paintComponent(Graphics g) {
        drawFlag(g, Color.BLACK, Color.RED, Color.YELLOW, 50, 100, 100);
        drawFlag(g, Color.RED, Color.WHITE, Color.GREEN, 50, 200, 100);
    }
}
