package edu.uchicago.gerber._05dice.P11_9;

import javax.swing.*;

public class Driver {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(1000,1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        CircleComponent component = new CircleComponent();
        frame.add(component);

        frame.setVisible(true);
    }
}
