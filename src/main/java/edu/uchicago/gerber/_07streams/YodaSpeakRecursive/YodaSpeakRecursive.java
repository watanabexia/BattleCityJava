package edu.uchicago.gerber._07streams.YodaSpeakRecursive;

public class YodaSpeakRecursive {
    public String reverse(String words) {
        int last_space_index = words.lastIndexOf(" ");
        if (last_space_index == -1) {
            return words;
        }
        return words.substring(last_space_index + 1) + " " + reverse(words.substring(0 , last_space_index));
    }
}
