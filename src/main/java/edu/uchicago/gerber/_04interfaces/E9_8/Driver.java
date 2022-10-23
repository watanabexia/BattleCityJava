package edu.uchicago.gerber._04interfaces.E9_8;

public class Driver {
    public static void main(String[] args) {
        BasicAccount b = new BasicAccount();
        b.deposit(100);
        System.out.println(b.getBalance());

        //withdraw more than balance
        b.withdraw(120);

        //withdraw less than balance
        b.withdraw(50);
        System.out.println(b.getBalance());
    }
}
