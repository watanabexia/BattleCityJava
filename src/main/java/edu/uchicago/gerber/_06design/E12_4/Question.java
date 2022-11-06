package edu.uchicago.gerber._06design.E12_4;

public class Question {
    private int a,b,ans;
    private char operator;

    public void setA(int a) {
        this.a = a;
    }

    public void setB(int b) {
        this.b = b;
    }

    public void setOperator(char operator) {
        this.operator = operator;
    }

    public void setAns(int ans) {
        this.ans = ans;
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    public char getOperator() {
        return operator;
    }

    public int getAns() {
        return ans;
    }

    @Override
    public String toString() {
        return a + Character.toString(operator) + b + "=";
    }
}
