package edu.uchicago.gerber._03objects.P8_8;

public class VotingMachine {
    private int Democrat;
    private int Republican;

    public VotingMachine() {
        this.Democrat = 0;
        this.Republican = 0;
    }

    public void clear() {
        this.Democrat = 0;
        this.Republican = 0;
    }

    public void voteDemo() {
        this.Democrat++;
    }

    public void voteRepub() {
        this.Republican++;
    }

    public int getDemo() {
        return this.Democrat;
    }

    public int getRepub() {
        return this.Republican;
    }
}
