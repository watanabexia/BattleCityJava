package edu.uchicago.gerber._06design.P12_1;

public class Driver {
    public static void main(String[] args) {
        VendingMachine vm = new VendingMachine();

        // Operator adds coins to the vending machine as changes
        vm.add_coin(new Coin(10), 1);
        vm.add_coin(new Coin(5), 2);
        vm.add_coin(new Coin(1), 5);

        // Show all the available coins in the vending machine
        System.out.println(vm.check_coins());

        // Operator restocks products
        vm.restock(new Product("Cocacola", 3), 1);
        vm.restock(new Product("Cheezit", 2), 3);

        // Show all products and quantities in the vending machine
        System.out.println(vm.check_products());

        // Show all available products for customer to choose, NAME = PRICE
        System.out.println(vm.display_products());

        // Customer select a product from the list, the balance is changed
        vm.select("Cocacola");
        System.out.println(vm.getBalance());

        // Customer insert coin, the payment is changed. The payment is still
        // insufficient to get the product, thus nothing happened.
        System.out.println(vm.insert_coin(new Coin(1)));
        System.out.println(vm.insert_coin(new Coin(0.5)));
        System.out.println(vm.getPayment());

        // The payment is insufficient, and the Customer wants the money back
        System.out.println(vm.return_coins());

        // Customer inserts two coins. The payment is sufficient. Customer gets the
        // changes.
        System.out.println(vm.insert_coin(new Coin(2)));
        System.out.println(vm.insert_coin(new Coin(5)));
        System.out.println(vm.check_products());
        System.out.println(vm.check_coins());

        // Customer select Cocacola and insert sufficient coins. However, the coins
        // are returned because the product is sold out.
        vm.select("Cocacola");
        System.out.println(vm.insert_coin(new Coin(2)));
        System.out.println(vm.insert_coin(new Coin(3)));
        System.out.println(vm.check_products());

        // Customer select Cheezit and insert coins with too much value. Although
        // the product is not sold out, the machine can not give the changes and
        // thus the inserted coin is returned.
        vm.select("Cheezit");
        System.out.println(vm.insert_coin(new Coin(100)));
        System.out.println(vm.check_products());


        // Operator remove all the coins
        System.out.println(vm.remove_coins());
        System.out.println(vm.check_coins());
    }
}
