package edu.uchicago.gerber._04interfaces.P9_6;

public class Onetime extends Appointment {
    public Onetime(String description, int year, int month, int day) {
        super(description, year, month, day);
    }

    public boolean occursOn(int year, int month, int day) {
        return year == super.getYear()
                && month == super.getMonth()
                && day == super.getDay();
    }

    public String toString() {
        return "A onetime appointment{" +
                "description='" + super.getDescription() + '\'' +
                ", year=" + super.getYear() +
                ", month=" + super.getMonth() +
                ", day=" + super.getDay() +
                '}';
    }
}
