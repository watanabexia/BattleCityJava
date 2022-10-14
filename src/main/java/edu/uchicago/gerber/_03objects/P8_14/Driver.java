package edu.uchicago.gerber._03objects.P8_14;

import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.print("Enter the number of countries:");
        int n = s.nextInt();
        s.nextLine();
        Country[] countries = new Country[n];

        double max_area = -1, max_popuden = -1;
        int max_popu = -1;
        int max_area_idx = -1, max_popu_idx = -1, max_popuden_idx = -1;

        for (int i = 0; i < n; i++) {
            System.out.print("Enter the name of the country:");
            String name = s.nextLine();
            System.out.print("Enter the population of " + name + ":");
            int population = s.nextInt();
            s.nextLine();
            System.out.print("Enter the area of " + name + ":");
            double area = s.nextDouble();
            s.nextLine();
            countries[i] = new Country(name, population, area);
            double popuden = population/area;

            if (max_popu == -1) {
                max_popu = population;
                max_popu_idx = i;
            } else if (population > max_popu) {
                max_popu = population;
                max_popu_idx = i;
            }

            if (max_area == -1) {
                max_area = area;
                max_area_idx = i;
            } else if (area > max_area) {
                max_area = area;
                max_area_idx = i;
            }

            if (max_popuden == -1) {
                max_popuden = popuden;
                max_popuden_idx = i;
            } else if (popuden > max_popuden) {
                max_popuden = popuden;
                max_popuden_idx = i;
            }
        }

        s.close();

        System.out.println(countries[max_area_idx].getName());
        System.out.println(countries[max_popu_idx].getName());
        System.out.println(countries[max_popuden_idx].getName());
    }
}
