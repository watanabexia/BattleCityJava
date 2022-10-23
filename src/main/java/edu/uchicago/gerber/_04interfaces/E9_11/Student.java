package edu.uchicago.gerber._04interfaces.E9_11;

public class Student extends Person {
    private final String major;

    public Student(String name, int yearofbirth, String major) {
        super(name, yearofbirth);
        this.major = major;
    }

    public String getMajor() {
        return major;
    }

    public String toString() {
        return "Student{" +
                "name='" + super.getName() + '\'' +
                ", yearofbirth=" + super.getYearofbirth() +
                ", major='" + major + '\'' +
                '}';
    }
}
