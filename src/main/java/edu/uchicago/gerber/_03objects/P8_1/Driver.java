package edu.uchicago.gerber._03objects.P8_1;

public class Driver {
    public static void main(String[] args) {
        Microwave m = new Microwave();
        m.increase30s();
        m.start();
        m.reset();
        m.switchPowerLevel();
        m.start();
        m.switchPowerLevel();
        m.start();
    }
}
