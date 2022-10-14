package edu.uchicago.gerber._03objects.P8_6;

public class Car {
    private final double fuelEfficiency;
    private double gasLevel;

    public Car(double fuelEfficiency) {
        this.fuelEfficiency = fuelEfficiency;
        this.gasLevel = 0;
    }

    public void addGas(double gas) {
        this.gasLevel += gas;
    }

    public void drive(double distance) {
        this.gasLevel -= distance/this.fuelEfficiency;
    }

    public double getGasLevel() {
        return this.gasLevel;
    }
}
