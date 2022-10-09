package edu.uchicago.gerber._02arrays;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class E7_4 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("Please type the input file name:");
        String input = s.nextLine();
        File inputf = new File(input);

        try {
            Scanner s_if = new Scanner(inputf);

            System.out.println("Please type the output file name:");
            String output = s.nextLine();

            PrintWriter w_of = new PrintWriter(output);

            int line_count = 1;
            while (s_if.hasNextLine()) {
                String line = s_if.nextLine();

                //Debug
//                System.out.println(line);

                w_of.print("/* " + line_count + " */ ");
                w_of.println(line);
                line_count++;
            }

            s_if.close();
            w_of.close();

        } catch (FileNotFoundException e) {
            System.out.println("The input file does not exist.");
        }
    }
}
