package edu.uchicago.gerber._05dice.P10_19;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BillFrame extends JFrame {
    private static final int FRAME_WIDTH = 400;
    private static final int FRAME_HEIGHT = 1000;

    //BILL
    private BillPanel bill;
    private static final double TAX_RATE = 0.1025;
    private static final double TIPS_RATE = 0.2;
    static class BillPanel extends JPanel {
        private double subtotal;
        private double tax;
        private double tips;
        private double total;

        private JTextArea bill_text;

        private void Add_meta_info() {
            bill_text.append("\n");
            bill_text.append("Subtotal: $" + String.format("%.2f",subtotal) + "\n");
            bill_text.append("Tax (" + TAX_RATE*100 + "%): $" + String.format("%.2f",tax) + "\n");
            bill_text.append("Suggested Tips (" + TIPS_RATE*100 + "%): $" + String.format("%.2f",tips) + "\n");
            bill_text.append("Total: $" + String.format("%.2f",total) + "\n");
        }

        private void Delete_meta_info() {
            String bill_text_copy = bill_text.getText();
            for (int i = 0; i < 5; i++) {
                int idx = bill_text_copy.lastIndexOf("\n");
                bill_text_copy = bill_text_copy.substring(0, idx);
            }
            bill_text.setText(bill_text_copy);
        }

        private void update_meta_info() {
            tax = subtotal * TAX_RATE;
            tips = subtotal * TIPS_RATE;
            total = subtotal + tax + tips;
        }

        public void Add_entry(String name, double price) {
            Delete_meta_info();
            bill_text.append(name + " $" + price + "\n");
            subtotal += price;
            update_meta_info();
            Add_meta_info();
        }

        private void Clear_bill() {
            bill_text.setText("");
            subtotal = 0;
            update_meta_info();
            Add_meta_info();
        }

        public BillPanel() {
            subtotal = 0;
            update_meta_info();

            createComponents();
        }

        class ClearListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                Clear_bill();
            }
        }

        private void createComponents() {
            setLayout(new BorderLayout());

            JLabel l = new JLabel("BILL SUMMARY");
            add(l, BorderLayout.NORTH);

            bill_text = new JTextArea(15, 30);
            bill_text.setEditable(false);
            Add_meta_info();
            JScrollPane scrollPane = new JScrollPane(bill_text);
            add(scrollPane, BorderLayout.CENTER);

            JButton clear = new JButton("CLEAR");
            ClearListener listener = new ClearListener();
            clear.addActionListener(listener);
            add(clear, BorderLayout.SOUTH);
        }
    }

    class DishPanel extends JPanel {

        private final String[] names = {"Crispy Chicken Sandwich",
                "Crispy Chicken Sandwich Meal",
                "Big Mac Meal",
                "Double Quarter Pounder with Cheese Meal",
                "10 Piece McNuggets Meal",
                "20 Piece McNuggets",
                "40 McNuggets",
                "Medium French Fires",
                "Regular Oreo McFlurry",
                "Medium Coke"};
        private final double[] prices = {5.96,
                8.17,
                10.65,
                11.86,
                10.99,
                8.39,
                13.25,
                3.37,
                4.67,
                1.69};

        private JTextField name_other, price_other;

        class PopularDishButton extends JButton {
            private final String name;
            private final double price;

            class ClickListener implements ActionListener {
                public void actionPerformed(ActionEvent event) {
                    bill.Add_entry(name, price);
                }
            }

            public PopularDishButton(String name, double price) {
                super(name + " $" + price);
                this.name = name;
                this.price = price;
                ActionListener listener = new ClickListener();
                addActionListener(listener);
            }
        }

        public DishPanel() {
            setLayout(new GridLayout(names.length + 5,1));
            createPopularDishButtons();
            createOtherDish();
        }

        private void createPopularDishButtons() {
            for (int i = 0; i < names.length; i++) {
                PopularDishButton button = new PopularDishButton(names[i], prices[i]);
                add(button);
            }
        }

        class OtherClickListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                double price = Double.parseDouble(price_other.getText());
                bill.Add_entry(name_other.getText(), price);
            }
        }

        private void createOtherDish() {
            JLabel l1 = new JLabel("NAME");
            add(l1);
            name_other = new JTextField();
            add(name_other);
            JLabel l2 = new JLabel("PRICE");
            add(l2);
            price_other = new JTextField();
            add(price_other);
            JButton add_other = new JButton("ADD");
            OtherClickListener listener = new OtherClickListener();
            add_other.addActionListener(listener);
            add(add_other);
        }
    }

    public BillFrame() {
        createComponents();

        setTitle("Bill");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
    }

    private void createComponents() {
        JPanel main = new JPanel();

        bill = new BillPanel();
        main.add(bill);

        //DISH
        DishPanel dish = new DishPanel();
        main.add(dish);

        add(main);
    }
}
