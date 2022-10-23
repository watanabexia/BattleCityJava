package edu.uchicago.gerber._04interfaces.E9_11;

public class Person {
    private final String name;
    private final int yearofbirth;

    public Person(String name, int yearofbirth) {
        this.name = name;
        this.yearofbirth = yearofbirth;
    }

    public String getName() {
        return name;
    }

    public int getYearofbirth() {
        return yearofbirth;
    }

    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", yearofbirth=" + yearofbirth +
                '}';
    }
}
