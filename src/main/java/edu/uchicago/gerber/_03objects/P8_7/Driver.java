package edu.uchicago.gerber._03objects.P8_7;

public class Driver {
    public static void main(String[] args) {
        ComboLock l = new ComboLock(36,21,15);
        System.out.println(l.open());
//        l.turnRight(36);
//        l.turnLeft(15);
//        l.turnRight(34);
//        l.turnRight(12);
//        l.turnRight(24);
//        l.turnLeft(15);
//        l.turnRight(34);
        System.out.println(l.open());
    }
}
