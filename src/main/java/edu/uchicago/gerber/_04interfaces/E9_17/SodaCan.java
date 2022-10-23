package edu.uchicago.gerber._04interfaces.E9_17;

public class SodaCan implements Measurable{

    private final double height;
    private final double radius;

    public SodaCan(double height, double radius) {
        this.height = height;
        this.radius = radius;
    }

    public double getSurfaceArea() {
        return  2 * Math.PI * radius * height + 2 * Math.PI * radius * radius;
    }

    public double getVolume() {
        return  Math.PI * radius * radius * height;
    }

    public double getMeasure() {
        return getSurfaceArea();
    }
}
