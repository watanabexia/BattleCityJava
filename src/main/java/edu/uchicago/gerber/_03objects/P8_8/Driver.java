package edu.uchicago.gerber._03objects.P8_8;

public class Driver {
    public static void main(String[] args) {
        VotingMachine m = new VotingMachine();
        m.voteDemo();
        m.voteDemo();
        m.voteRepub();
        System.out.println(m.getDemo());
        System.out.println(m.getRepub());
        m.clear();
        System.out.println(m.getDemo());
        System.out.println(m.getRepub());
    }
}
