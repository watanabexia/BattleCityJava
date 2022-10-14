package edu.uchicago.gerber._03objects.P8_1;

public class Microwave {

    private int cookTime;
    private int powerLevel;

    public Microwave() {
        this.cookTime = 0;
        this.powerLevel = 1;
    }

    public void increase30s() {
        this.cookTime += 30;
    }

    public void switchPowerLevel() {
        if (this.powerLevel == 1) {
            this.powerLevel = 2;
        } else {
            this.powerLevel = 1;
        }
    }

    public void reset() {
        this.cookTime = 0;
        this.powerLevel = 1;
    }

    public void start() {
        System.out.println("Cooking for " + this.cookTime + " seconds at level " + this.powerLevel);
    }
}
