package edu.uchicago.gerber._02arrays;

public class P5_8 {
    public static void main(String[] args) {
        //Debug
        System.out.println(isLeapYear(Integer.parseInt(args[0])));
    }

    public static boolean isLeapYear(int year) {
        if ((year % 100) == 0) {
            return (year % 400) == 0;
        } else {
            return (year % 4) == 0;
        }
    }
}
