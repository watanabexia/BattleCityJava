package edu.uchicago.gerber._02arrays;

public class P5_24 {
    public static void main(String[] args) {
        int total = 0;
        String romanStr = args[0];
        while (romanStr.length() > 0) {
            if (romanStr.length() == 1) {
                int value_0 = value(romanStr.charAt(0));
                total += value_0;
                romanStr = "";
            } else {
                int value_0 = value(romanStr.charAt(0));
                int value_1 = value(romanStr.charAt(1));
                if (value_0 >= value_1) {
                    total += value_0;
                    romanStr = romanStr.substring(1);
                } else {
                    int diff = value_1 - value_0;
                    total += diff;
                    if (romanStr.length() > 2) {
                        romanStr = romanStr.substring(2);
                    } else {
                        romanStr = "";
                    }
                }
            }
        }
        //Debug
        System.out.println(total);
    }

    public static int value(char romanChar) {
        switch (romanChar) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            case 'D':
                return 500;
            case 'M':
                return 1000;
            default:
                return -1;
        }
    }
}
