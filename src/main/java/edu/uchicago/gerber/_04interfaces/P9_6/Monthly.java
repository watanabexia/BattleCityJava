package edu.uchicago.gerber._04interfaces.P9_6;

public class Monthly extends Appointment {
    public Monthly(String description, int year, int month, int day) {
        super(description, year, month, day);
    }

    public boolean occursOn(int year, int month, int day) {
        return year >= super.getYear()
                && month >= super.getMonth()
                && day == super.getDay();
    }

    public String toString() {
        return "A monthly appointment{" +
                "description='" + super.getDescription() + '\'' +
                ", year=" + super.getYear() +
                ", month=" + super.getMonth() +
                ", day=" + super.getDay() +
                '}';
    }
}
