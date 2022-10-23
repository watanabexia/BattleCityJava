package edu.uchicago.gerber._04interfaces.E9_11;

public class Instructor extends Person {
    private final double salary;

    Instructor(String name, int yearofbirth, double salary) {
        super(name, yearofbirth);
        this.salary = salary;
    }

    public double getSalary() {
        return salary;
    }

    public String toString() {
        return "Instructor{" +
                "name='" + super.getName() + '\'' +
                ", yearofbirth=" + super.getYearofbirth() +
                ", salary=" + salary +
                '}';
    }
}
