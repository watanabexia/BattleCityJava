package edu.uchicago.gerber._03objects.P8_7;

public class ComboLock {
    private int dial;
    private final int secret1;
    private final int secret2;
    private final int secret3;
    private boolean check1;
    private boolean check2;
    private boolean check3;

    public ComboLock(int secret1, int secret2, int secret3) {
        this.dial = 0;
        this.secret1 = secret1;
        this.secret2 = secret2;
        this.secret3 = secret3;
        this.check1 = false;
        this.check2 = false;
        this.check3 = false;
    }

    public void reset() {
        this.dial = 0;
    }

    public void turnLeft(int ticks) {
        this.dial -= ticks;
        this.dial %= 40;
        if (this.check1) {
            if (this.dial == this.secret2) {
                this.check2 = true;
            }
        }
    }

    public void turnRight(int ticks) {
        this.dial += ticks;
        this.dial %= 40;
        if (this.check1 && this.check2) {
            if (this.dial == this.secret3) {
                this.check3 = true;
            }
        } else if (!this.check1) {
            if (this.dial == this.secret1) {
                this.check1 = true;
            }
        }
    }

    public boolean open() {
        return this.check1 && this.check2 && this.check3;
    }
}
