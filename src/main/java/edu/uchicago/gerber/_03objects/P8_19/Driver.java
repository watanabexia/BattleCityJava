package edu.uchicago.gerber._03objects.P8_19;

import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.print("Enter the starting angle:");
        double a = s.nextDouble();
        s.nextLine();
        System.out.print("Enter the initial velocity:");
        double v = s.nextDouble();
        s.nextLine();

        s.close();

        Cannonball b = new Cannonball(0);
        b.shoot(a, v);
    }
}
