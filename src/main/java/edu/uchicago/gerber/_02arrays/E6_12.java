package edu.uchicago.gerber._02arrays;

import java.util.Arrays;

public class E6_12 {
    public static void main(String[] args) {
        int min = 0;
        int max = 99;
        double[] arr = new double[20];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = Math.random() * (max - min) + min;
        }
        for (double element : arr) {
            System.out.print(element);
            System.out.print(" ");
        }
        System.out.println();

        Arrays.sort(arr);

        for (double element : arr) {
            System.out.print(element);
            System.out.print(" ");
        }
        System.out.println();
    }
}
