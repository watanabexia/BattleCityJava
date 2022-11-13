package edu.uchicago.gerber._07streams.E13_4;

public class Binary {
    public String binary(int n) {
        if (n < 2) {
            return Integer.toString(n);
        }
        if (n % 2 == 0) {
            return binary(n / 2) + "0";
        } else {
            return binary(n / 2) + "1";
        }
    }
}
