package edu.uchicago.gerber._04interfaces.E9_11;

public class Driver {
    public static void main(String[] args) {
        Student stu = new Student("Qingyang", 1999, "Computer Science");
        Instructor ins = new Instructor("Steve", 1980, 13138.65);

        System.out.println("The test student is " + stu);
        System.out.println("The test instructor is " + ins);
    }
}
