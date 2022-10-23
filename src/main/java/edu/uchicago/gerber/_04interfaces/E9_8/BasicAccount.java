package edu.uchicago.gerber._04interfaces.E9_8;

public class BasicAccount extends BankAccount{

    public void withdraw(double amount) {
        if (amount > getBalance()) {
            System.out.println("Insufficient balance.");
        } else {
            super.withdraw(amount);
        }
    }
}
