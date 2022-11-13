package edu.uchicago.gerber._07streams.E19_16;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Driver {

    private static final String file_path = "words.txt";

    public static void main(String[] args) {
        ArrayList<String> words = new ArrayList<>();

        try {

            Scanner s = new Scanner(new File(file_path));

            while(s.hasNext()) {
                String word = s.next();
                words.add(word);
            }

            Stream<String> wordStream = words.stream();
            wordStream.collect(
                            Collectors.groupingBy(word -> Character.toLowerCase(word.charAt(0)),
                                    Collectors.averagingInt(String::length))
                    )
                    .forEach((k, v) -> System.out.println(k + ":" + v));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
