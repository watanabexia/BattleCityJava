package edu.uchicago.gerber._07streams.E19_6;

import java.util.Currency;
import java.util.Set;
import java.util.stream.Stream;

public class Driver {
    public static void main(String[] args) {
        Set<Currency> currencySet = Currency.getAvailableCurrencies();
        Stream<Currency> currencyStream = currencySet.stream();

        Stream<String> displaynameStream = currencyStream.map(Currency::getDisplayName).sorted();
        displaynameStream.forEach(System.out::println);
    }
}
