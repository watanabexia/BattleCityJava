package edu.uchicago.gerber._05dice.P11_9;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CircleComponent extends JComponent {
    private int center_x, center_y, radius;

    class CircleListener implements MouseListener {
        public void mousePressed(MouseEvent e) {
            int x = e.getX(), y = e.getY();
            if (center_x == -1 && center_y == -1) {
                center_x = x;
                center_y = y;
            } else {
                radius = (int) Math.sqrt((x - center_x)*(x - center_x) + (y-center_y)*(y-center_y));
                repaint();
            }
        }

        public void mouseClicked(MouseEvent e) {}
        public void mouseEntered(MouseEvent e) {}
        public void mouseExited(MouseEvent e) {}
        public void mouseReleased(MouseEvent e) {}
    }

    public CircleComponent() {
        center_x = -1;
        center_y = -1;
        radius = -1;

        CircleListener listener = new CircleListener();
        addMouseListener(listener);
    }

    public void paintComponent(Graphics g) {
        if (center_x != -1 && center_y != -1) {
            g.setColor(Color.BLACK);
            g.drawOval(center_x - radius, center_y - radius, 2*radius, 2*radius);
            center_x = -1;
            center_y = -1;
        }
    }
}
