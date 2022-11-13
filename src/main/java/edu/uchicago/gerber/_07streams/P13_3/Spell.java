package edu.uchicago.gerber._07streams.P13_3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Spell {
    private ArrayList<String> dictionary;

    //Reference: https://wiley.com/go/bjlo2code
    private static final String dictionary_path = "words.txt";

    private static final String[] phone_pad = {"", "", "ABC", "DEF", "GHI", "JKL", "MNO", "PQRS", "TUV", "WXYZ"};
    private final ArrayList<Integer> del_default;

    public Spell() {

        //Debug
//        System.out.println(System.getProperty("user.dir"));

        initialize_dictionary();
        del_default = new ArrayList<>();
        del_default.add(0);

        //Debug
//        System.out.print(dictionary.get(2));
    }

    private void initialize_dictionary() {
        dictionary = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(dictionary_path));
            String line = reader.readLine();
            while(line != null) {
                line = line.toUpperCase();
                dictionary.add(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void tokenize(String raw, int index, ArrayList<Integer> del) {

        int last_del = del.get(del.size() - 1);

        if (index < raw.length()) {

            for (int i = index; i < raw.length(); i++) {

                // Testing if raw[last_del...index] is a valid token
                String token = raw.substring(last_del, i + 1);

                //Debug
//                System.out.println("TESTING " + raw + " with lastdel >> " + last_del + " with i >> " + i +  " with del >> " + del + " current token >> " + token);

                if (dictionary.contains(token)) {

                    ArrayList<Integer> del_new = new ArrayList<>(del);
                    del_new.add(i + 1);

                    //Debug
//                    System.out.println("TOKEN MATCHED! NEXT INDEX...");

                    tokenize(raw, i + 1, del_new);
                }
            }
        } else {
            if (last_del == raw.length()) {
                for (int i = 0; i < del.size() - 1; i++) {
                    System.out.print(raw.substring(del.get(i), del.get(i+1)) + " ");
                }
                System.out.println();
            }
        }
    }

    public void spell(String number, int char_index, String spelling) {
        if (char_index < number.length()) {
            int digit = Integer.parseInt(number.charAt(char_index) + "");
            for (int i = 0; i < phone_pad[digit].length(); i++) {
                spell(number, char_index + 1, spelling + phone_pad[digit].charAt(i));
            }
        } else {

            //Debug
//            System.out.println(spelling);

            tokenize(spelling, 0, del_default);
        }
    }
}
