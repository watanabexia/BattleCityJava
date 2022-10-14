package edu.uchicago.gerber._03objects.P8_19;

public class Cannonball {
    private double x, y, v_x, v_y;

    public Cannonball(double x) {
        this.x = x;
        this.y = 0;
//        this.v_x = 0;
//        this.v_y = 0;
    }

    public void move(double sec) {
        this.x += this.v_x * sec;
        this.y += this.v_y * sec + 0.5 * (-9.81) * sec * sec;
        this.v_y += -9.81 * sec;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void shoot(double a, double v) {
        this.v_x = v * Math.cos(a);
        this.v_y = v * Math.sin(a);
        while (this.y >= 0) {
            System.out.println(getX() + "," + getY());
            move(0.1);
        }
    }
}
