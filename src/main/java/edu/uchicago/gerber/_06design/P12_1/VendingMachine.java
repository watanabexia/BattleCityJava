package edu.uchicago.gerber._06design.P12_1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class VendingMachine {
    private String selected_product;
    private double balance;

    private final HashMap<String, Double> product_prices;
    private final HashMap<String, Integer> products;

    private double coin_value;
    private HashMap<Double, Integer> coins;
    private ArrayList<Double> coin_types;

    private double payment;
    private HashMap<Double, Integer> inserted_coins;

    public VendingMachine() {
        selected_product = null;
        balance = 0;

        products = new HashMap<>();
        product_prices = new HashMap<>();

        coin_value = 0;
        coins = new HashMap<>();
        coin_types = new ArrayList<>();

        payment = 0;
        inserted_coins = new HashMap<>();
    }

    public void restock(Product product, int quantity) {
        String name = product.getName();
        if (products.containsKey(name)) {
            products.put(name, products.get(name) + quantity);
        } else {
            products.put(name, quantity);
            product_prices.put(name, product.getPrice());
        }
    }

    public HashMap<String, Double> display_products() {
        return product_prices;
    }

    public HashMap<String, Integer> check_products() {
        return products;
    }

    public double getBalance() {
        return balance;
    }

    public double getPayment() {
        return payment;
    }

    public void select(String product_name) {
        if (product_prices.containsKey(product_name)) {
            selected_product = product_name;
            balance = product_prices.get(product_name);
        }
    }

    public void add_coin(Coin coin, int quantity) {
        double value = coin.getValue();
        if (coins.containsKey(value)) {
            coins.put(value, coins.get(value) + quantity);
        } else {
            coins.put(value, quantity);
            coin_types.add(value);
            coin_types.sort(Collections.reverseOrder());
        }
        coin_value += value;
    }

    public HashMap<Double, Integer> check_coins() {
        return coins;
    }

    public HashMap<Double, Integer> remove_coins() {
        HashMap<Double, Integer> coins_remove = coins;
        coins = new HashMap<>();
        coin_types = new ArrayList<>();
        coin_value = 0;
        return coins_remove;
    }

    public HashMap<Double, Integer> insert_coin(Coin coin) {

        double value = coin.getValue();
        if (inserted_coins.containsKey(value)) {
            inserted_coins.put(value, inserted_coins.get(value) + 1);
        } else {
            inserted_coins.put(value, 1);
        }
        payment += value;

        if (selected_product == null) { // item not selected
            return return_coins();

        } else if (payment < balance) { // insufficient payment
            return new HashMap<>();

        } else { // sufficient payment

            // Check if the product is sold out
            if (products.get(selected_product) == 0) {
                selected_product = null;
                return return_coins();
            }

            // Check if we can have a change
            double changes = payment - balance;

            HashMap<Double, Integer> changes_coins = new HashMap<>();
            for (int i = 0; i < coin_types.size() && changes > 0; i++) {
                double coin_value = coin_types.get(i);

                if (changes >= coin_value) {
                    changes_coins.put(coin_value, 0);
                    while (changes >= coin_value && coins.get(coin_value) > changes_coins.get(coin_value)) {

                        changes -= coin_value;
                        changes_coins.put(coin_value, changes_coins.get(coin_value) + 1);
                    }
                }
            }

            if (changes == 0) {
                balance = 0;
                products.put(selected_product, products.get(selected_product) - 1);
                selected_product = null;

                payment = 0;
                // Add in payment
                for (Map.Entry<Double, Integer> set : inserted_coins.entrySet()) {
                    add_coin(new Coin(set.getKey()), set.getValue());
                }

                // Clear inserted_coins
                inserted_coins = new HashMap<>();

                // Remove changes
                for (Map.Entry<Double, Integer> set : changes_coins.entrySet()) {
                    coins.put(set.getKey(), coins.get(set.getKey()) - set.getValue());
                }

                return changes_coins;

            } else {
                return return_coins();
            }
        }
    }

    public HashMap<Double, Integer> return_coins() {
        HashMap<Double, Integer> return_coins = inserted_coins;
        inserted_coins = new HashMap<>();
        payment = 0;

        return return_coins;
    }
}
