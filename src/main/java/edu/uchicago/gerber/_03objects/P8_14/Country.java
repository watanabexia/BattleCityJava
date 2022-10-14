package edu.uchicago.gerber._03objects.P8_14;

public class Country {
    private final String name;
    private final int population;
    private final double area;

    public Country(String name, int population, double area) {
        this.name = name;
        this.population = population;
        this.area = area;
    }

    public String getName() {
        return this.name;
    }

    public int getPopulation() {
        return this.population;
    }

    public double getArea() {
        return this.area;
    }
}
