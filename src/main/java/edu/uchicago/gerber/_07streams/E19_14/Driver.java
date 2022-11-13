package edu.uchicago.gerber._07streams.E19_14;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Driver {

    private static final String file_path = "WarAndPeace.txt";

    public static void main(String[] args) {
        ArrayList<String> words = new ArrayList<>();

        try {
            Scanner s = new Scanner(new File(file_path));

            while(s.hasNext()) {
                String word = s.next();
                words.add(word);
            }

            Stream<String> wordStream = words.stream().parallel();
            String result = wordStream.filter(word -> word.length() >= 5)
                    .filter(word -> word.equals(new StringBuilder(word).reverse().toString()))
                    .findAny()
                    .orElse("No palindrome is found.");

            System.out.println(result);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
