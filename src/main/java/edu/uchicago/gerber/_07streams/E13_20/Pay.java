package edu.uchicago.gerber._07streams.E13_20;

import java.util.ArrayList;

public class Pay {
    private static final int[] bills = {100, 20, 5, 1};

    public void pay(int price, int bill_index, ArrayList<Integer> bills_num) {
        if (bill_index == bills.length) {
            if (price == 0) {
                for (int i = 0; i < bills.length; i++) {
                    System.out.print("$" + bills[i] + ":" + bills_num.get(i) + " ");
                }
                System.out.println();
            }
        } else {
            int count = 0;
            while(price - bills[bill_index] * count >= 0) {
                ArrayList<Integer> bills_num_new = new ArrayList<>(bills_num);
                bills_num_new.add(count);
                pay(price - bills[bill_index] * count, bill_index + 1, bills_num_new);
                count++;
            }
        }
    }
}
