package edu.uchicago.gerber._07streams.YodaSpeakRecursive;

import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.print("Please give me a sentence: ");

        YodaSpeakRecursive ys = new YodaSpeakRecursive();
        System.out.println(ys.reverse(s.nextLine()));
    }
}