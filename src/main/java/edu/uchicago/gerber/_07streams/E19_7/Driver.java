package edu.uchicago.gerber._07streams.E19_7;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Stream;

public class Driver {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        System.out.print("The number of words: ");
        int n = s.nextInt();

        ArrayList<String> words = new ArrayList<>();

        System.out.println("Now input the words. One word for each line:");
        s.nextLine();

        for (int i = 0; i < n; i++) {
            words.add(s.nextLine());
        }

        Stream<String> wordStream = words.stream();

        wordStream.filter(word -> word.length() > 2)
                .map(word -> word.charAt(0) + "..." + word.charAt(word.length() - 1))
                .forEach(System.out::println);
    }
}
