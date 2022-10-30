package edu.uchicago.gerber._05dice.P10_10;

import edu.uchicago.gerber._05dice.P10_9.FlagComponent;

import javax.swing.*;

public class Driver {
    public static void main(String[] args) {
        JFrame frame = new JFrame();

        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        RingComponent component = new RingComponent();

        frame.add(component);

        frame.setVisible(true);
    }
}
