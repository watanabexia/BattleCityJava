package edu.uchicago.gerber._04interfaces.E9_17;

import java.util.Scanner;

public class Driver {
    public static double average(Measurable[] objects) {
        if (objects.length == 0) { return 0; }
        double sum = 0;
        for (Measurable obj : objects) {
            sum = sum + obj.getMeasure();
        }
        return sum / objects.length;
    }

    public static void main(String[] args) {
        int n;
        Scanner s = new Scanner(System.in);
        System.out.print("Input the number of soda cans:");
        n = s.nextInt();
        s.nextLine();
        SodaCan[] sodaCans = new SodaCan[n];
        for (int i = 0; i < sodaCans.length; i++) {
            System.out.print("Input the height and radius of soda can #" + i + ", separate with a space:");
            double h = s.nextDouble();
            double r = s.nextDouble();
            sodaCans[i] = new SodaCan(h, r);
            s.nextLine();
        }

        System.out.println("The average surface area is " + average(sodaCans));
    }
}
