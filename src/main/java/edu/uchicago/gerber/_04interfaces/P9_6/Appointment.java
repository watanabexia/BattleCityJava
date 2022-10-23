package edu.uchicago.gerber._04interfaces.P9_6;

public class Appointment {
    private final String description;
    private final int year, month, day;

    public Appointment(String description, int year, int month, int day) {
        this.description = description;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public String getDescription() {
        return description;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public boolean occursOn(int year, int month, int day) {
        return year == getYear()
                && month == getMonth()
                && day == getDay();
    }

    public String toString() {
        return "An appointment{" +
                "description='" + description + '\'' +
                ", year=" + year +
                ", month=" + month +
                ", day=" + day +
                '}';
    }
}
