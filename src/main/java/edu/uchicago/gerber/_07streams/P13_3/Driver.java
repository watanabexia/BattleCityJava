package edu.uchicago.gerber._07streams.P13_3;

import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {

        Spell sp = new Spell();

        System.out.print("Give me the number: ");

        Scanner s = new Scanner(System.in);

        String number = Long.toString(s.nextLong());
        // If you give a long number, the next step may
        // take a long time. Because in the given dictionary words.txt,
        // a single character is also considered as a valid word, resulting
        // in the tremendous growth of the number of valid word sequences.
        // You may reduce the running time by removing all single characters line
        // in words.txt, or supply your own dictionary file.
        sp.spell(number, 0, "");
    }
}
