package edu.uchicago.gerber._06design.E12_4;

import java.util.Random;
import java.util.Scanner;

public class Arithmetic {
    private int level, score;

    public Arithmetic() {
        this.level = 1;
        this.score = 0;
    }

    private Question generate_question() {
        Question q = new Question();
        Random rand = new Random();
        if (level == 1) {
            q.setOperator('+');
            int a = rand.nextInt(10);
            int b = rand.nextInt(10-a);
            int ans = a + b;
            q.setA(a);
            q.setB(b);
            q.setAns(ans);
        } else if (level == 2) {
            q.setOperator('+');
            int a = rand.nextInt(10);
            int b = rand.nextInt(10);
            int ans = a + b;
            q.setA(a);
            q.setB(b);
            q.setAns(ans);
        } else {
            q.setOperator('-');
            int a = rand.nextInt(10);
            int b = rand.nextInt(a+1);
            int ans = a - b;
            q.setA(a);
            q.setB(b);
            q.setAns(ans);
        }
        return q;
    }

    private void display_status() {
        System.out.println("Your current score is " + score);
        System.out.println("You are at level " + level);
    }

    private void display_question(Question q) {
        Scanner s = new Scanner(System.in);

        boolean correct = false;
        int trial = 1;

        while (trial <= 2) {
            System.out.print(q);
            int ans = s.nextInt();
            if (ans == q.getAns()) {
                System.out.println("Correct answer!");
                correct = true;
                break;
            } else {
                System.out.println("Incorrect answer.");
                trial++;
            }
        }

        if (correct) {
            score++;
        } else {
            score--;
        }

        if (score == 5) {
            level = 2;
        } else if (score == 10) {
            level = 3;
        }
    }

    public void start_round() {
        display_status();
        Question q = generate_question();
        display_question(q);
    }

    public void start() {
        while (this.score < 15) {
            start_round();
        }

        System.out.println("Congratulations! You passed the arithmetic test!");
    }
}
