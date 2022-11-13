package edu.uchicago.gerber._07streams.YodaSpeak;

import edu.uchicago.gerber._07streams.YodaSpeakRecursive.YodaSpeakRecursive;

import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.print("Please give me a sentence: ");

        YodaSpeak ys = new YodaSpeak();
        System.out.println(ys.reverse(s.nextLine()));
    }
}