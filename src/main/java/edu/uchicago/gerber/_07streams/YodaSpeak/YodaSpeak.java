package edu.uchicago.gerber._07streams.YodaSpeak;

public class YodaSpeak {
    public String reverse(String words) {
        String words_reverse;
        int last_space_index = words.lastIndexOf(" ");
        int last_last_space_index = words.length();

        String token = words.substring(last_space_index + 1, last_last_space_index);
        words_reverse = token;
        last_last_space_index = last_space_index;
        last_space_index = words.lastIndexOf(" ", last_last_space_index - 1);

        while (last_space_index != -1) {
            token = words.substring(last_space_index + 1, last_last_space_index);
            words_reverse = words_reverse + " " + token;
            last_last_space_index = last_space_index;
            last_space_index = words.lastIndexOf(" ", last_last_space_index - 1);
        }
        words_reverse = words_reverse + " " + words.substring(0, last_last_space_index);
        return words_reverse;
    }
}
