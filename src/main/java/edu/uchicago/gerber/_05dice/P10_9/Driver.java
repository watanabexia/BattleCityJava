package edu.uchicago.gerber._05dice.P10_9;

import javax.swing.*;
import java.awt.*;

public class Driver {
    public static void main(String[] args) {
        JFrame frame = new JFrame();

        frame.setSize(300, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        FlagComponent component = new FlagComponent();

        frame.add(component);

        frame.setVisible(true);
    }
}
