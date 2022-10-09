package edu.uchicago.gerber._02arrays;

import java.util.concurrent.ThreadLocalRandom;

public class E6_1 {
    public static void main(String[] args) {
        int[] intArr = new int[10];
        for (int i = 0; i < 10; i++) {
            intArr[i] = ThreadLocalRandom.current().nextInt();
        }

        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                System.out.print(intArr[i]);
                System.out.print(" ");
            }
        }
        System.out.println();

        for (int element : intArr) {
            if (element % 2 == 0) {
                System.out.print(element);
                System.out.print(" ");
            }
        }
        System.out.println();

        for (int i = 9; i >= 0; i--) {
            System.out.print(intArr[i]);
            System.out.print(" ");
        }
        System.out.println();

        System.out.print(intArr[0]);
        System.out.print(" ");
        System.out.print(intArr[9]);
        System.out.println();
    }
}
