package edu.uchicago.gerber._04interfaces.P9_6;

import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {

        Onetime a1 = new Onetime("see the dentist", 2022, 10, 20);
        Daily a2 = new Daily("study", 2022, 9, 1);
        Monthly a3 = new Monthly("call parents", 2022, 9, 20);
        Appointment[] appointments = {a1, a2, a3};

        Scanner s = new Scanner(System.in);
        System.out.print("Enter the date you want to check (DD MM YYYY):");
        int day = s.nextInt();
        int month = s.nextInt();
        int year = s.nextInt();

        for (Appointment a: appointments) {
            if (a.occursOn(year, month, day)) {
                System.out.println(a);
            }
        }
    }
}
