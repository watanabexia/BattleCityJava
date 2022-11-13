package edu.uchicago.gerber._07streams.E13_20;

import java.util.ArrayList;
import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        Pay p = new Pay();

        Scanner s = new Scanner(System.in);

        System.out.print("Please give me the integer price: ");

        int price = s.nextInt();

        // In each line,
        // each digit refers to the number of bills used, in the order of $100, $20, $5 and $1.
        p.pay(price, 0, new ArrayList<>());
    }
}
