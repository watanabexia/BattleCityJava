package edu.uchicago.gerber._04interfaces.E9_8;

public class BankAccount {
    private double balance;

    public void deposit(double amount) {
        balance = balance + amount;
    }

    public void withdraw(double amount) {
        balance = balance - amount;
    }

    public double getBalance() {
        return balance;
    }

    public void monthEnd() {}
}
