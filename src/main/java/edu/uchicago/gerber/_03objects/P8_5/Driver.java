package edu.uchicago.gerber._03objects.P8_5;

public class Driver {
    public static void main(String[] args) {
        SodaCan c = new SodaCan(1.6, 2.5);
        System.out.println(c.getVolume());
        System.out.println(c.getSurfaceArea());
    }
}
