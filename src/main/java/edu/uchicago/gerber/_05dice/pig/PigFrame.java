package edu.uchicago.gerber._05dice.pig;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class PigFrame extends JFrame {
    private static final int FRAME_WIDTH = 400;
    private static final int FRAME_HEIGHT = 400;
    private static final int WINNING_SCORE = 100;

    private int player_score, cpu_score, turn_score;

    private JLabel turn;
    private boolean player_turn;

    private JTextArea score_board, roll_result;
    private JButton start, roll, hold;
    class StartListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            startGame();
        }
    }
    class RollListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            hold.setEnabled(true);

            Random rand = new Random();
            int roll_num = (1 + rand.nextInt(6));
            roll_result.append(roll_num + " ");

            if (roll_num == 1) {
                roll.setEnabled(false);
                hold.setEnabled(false);

                roll_result.append("\n YOU EARNED 0 POINTS IN THIS TURN.");

                startNewTurn();
            } else {
                turn_score += roll_num;
            }
        }
    }
    class HoldListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            roll.setEnabled(false);
            hold.setEnabled(false);

            player_score += turn_score;
            updateScoreBoard();

            roll_result.append("\n YOU EARNED " + turn_score + " POINTS IN THIS TURN.");

            if (player_score >= WINNING_SCORE) {
                roll.setEnabled(false);
                hold.setEnabled(false);

                score_board.append("\n YOU WIN!!!!");

                endGame();
            } else {
                startNewTurn();
            }
        }
    }

    private int newturninterval;
    private Timer newTurnTimer;
    class NewTurnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            roll_result.append(newturninterval + " ");
            newturninterval--;
            if (newturninterval == -1) {
                newTurnTimer.stop();

                if (player_turn) {
                    player_turn = false;
                    startCPUTurn();
                } else {
                    player_turn = true;
                    startPlayerTurn();
                }
            }
        }
    }
    private void startNewTurn() {
        newturninterval = 2;
        roll_result.append("\n NEW TURN STARTS IN " + (newturninterval + 1) + " ");
        newTurnTimer = new Timer(1000, new NewTurnListener());
        newTurnTimer.start();
    }

    private void updateScoreBoard() {
        score_board.setText("PLAYER SCORE: " + player_score + "\n" +
                "CPU SCORE: " + cpu_score + "\n");
    }

    private void startGame() {
        start.setEnabled(false);

        player_score = 0;
        cpu_score = 0;
        updateScoreBoard();

        Random rand = new Random();
        int first = rand.nextInt(2);

//        player_turn = true;
//        startPlayerTurn();
        if (first == 0) {
            player_turn = true;
            startPlayerTurn();
        } else {
            player_turn = false;
            startCPUTurn();
        }
    }

    private void startPlayerTurn() {
        roll.setEnabled(true);
        turn.setText("(PLAYER TURN)");
        turn_score = 0;
        roll_result.setText("");
    }

    private int CPUHoldTime;
    private Timer CPURollTimer;
    class CPURollListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Random rand = new Random();
            int roll_num = (1 + rand.nextInt(6));
            roll_result.append(roll_num + " ");

            if (roll_num == 1) {
                CPURollTimer.stop();

                roll_result.append("\n CPU EARNED 0 POINTS IN THIS TURN.");

                startNewTurn();
            } else {
                turn_score += roll_num;
                CPUHoldTime--;
                if (CPUHoldTime == 0) {
                    CPURollTimer.stop();

                    cpu_score += turn_score;
                    updateScoreBoard();

                    roll_result.append("\n CPU EARNED " + turn_score + " POINTS IN THIS TURN.");

                    if (cpu_score >= WINNING_SCORE) {
                        score_board.append("\n CPU WIN :)");

                        endGame();
                    } else {
                        startNewTurn();
                    }
                }
            }
        }
    }

    private void startCPUTurn() {
        roll.setEnabled(false);
        hold.setEnabled(false);
        turn.setText("(CPU TURN)");
        turn_score = 0;
        roll_result.setText("");

        Random rand = new Random();
        CPUHoldTime = 1 + rand.nextInt(6);
        CPURollTimer = new Timer(300, new CPURollListener());
        CPURollTimer.start();
    }

    private void endGame() {
        start.setEnabled(true);
        roll.setEnabled(false);
        hold.setEnabled(false);
    }

    public PigFrame() {
        player_score = 0;
        cpu_score = 0;

        createComponents();

        setTitle("Pig Game");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
    }

    private void createComponents() {
        JPanel main = new JPanel();

        JLabel l1 = new JLabel("SCORE BOARD");
        main.add(l1);
        score_board = new JTextArea(3, 30);
        score_board.setEditable(false);
//        score_board.setLineWrap(true);
        main.add(score_board);

        JLabel l2 = new JLabel("ROLL RESULT");
        main.add(l2);
        turn = new JLabel();
        main.add(turn);
        roll_result = new JTextArea(5, 30);
        roll_result.setEditable(false);
        main.add(roll_result);

        start = new JButton("START");
        start.addActionListener(new StartListener());
        main.add(start);

        roll = new JButton("ROLL!");
        roll.addActionListener(new RollListener());
        roll.setEnabled(false);
        main.add(roll);

        hold = new JButton("HOLD");
        hold.addActionListener(new HoldListener());
        hold.setEnabled(false);
        main.add(hold);

        add(main);
    }
}
