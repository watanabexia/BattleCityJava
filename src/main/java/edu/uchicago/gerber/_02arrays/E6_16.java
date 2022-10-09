package edu.uchicago.gerber._02arrays;

import java.util.Arrays;

public class E6_16 {
    public static void main(String[] args) {
        double[] arr = new double[args.length];

        for (int i = 0; i < args.length; i++) {
            arr[i] = Double.parseDouble(args[i]);
        }

        double max = max(arr);
        //Debug
//        System.out.println(max);

        int[] ast_counts = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            ast_counts[i] = (int) (40 * (arr[i] / max));

            //Debug
//            System.out.println(ast_counts[i]);
        }

        for (int i = 0; i < 40; i++) {
            for (int j = 0; j < arr.length; j++) {
                if (40 - i <= ast_counts[j]) {
                    System.out.print("*");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    public static double max(double[] arr) {
        double max = arr[0];
        for (double element : arr) {
            if (element > max) {
                max = element;
            }
        }
        return max;
    }
}
