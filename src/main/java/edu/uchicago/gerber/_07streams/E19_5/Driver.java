package edu.uchicago.gerber._07streams.E19_5;

import java.util.stream.Stream;

public class Driver {
    public static void main(String[] args) {

        Stream<String> a = Stream.of("apple", "banana", "cyan", "dog", "friends");

        System.out.println(CommaList.toString(a, 3));

        Stream<Integer> b = Stream.of(1,2,3,4,5,6,7,8,9);

        System.out.println(CommaList.toString(b, 5));

    }
}
