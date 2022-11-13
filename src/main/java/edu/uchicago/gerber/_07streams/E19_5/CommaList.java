package edu.uchicago.gerber._07streams.E19_5;

import java.util.stream.Stream;

public class CommaList {
    public static <T> String toString(Stream<T> stream, int n) {
        String[] words = stream.limit(n).map(Object::toString).toArray(String[]::new);
        String result = "";
        for (String word :
             words) {
            result += word + ",";
        }
        return result.substring(0, result.length() - 1);
    }
}
