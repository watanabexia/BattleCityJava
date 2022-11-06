package edu.uchicago.gerber._06design.R12_11;

/**
 A country with a name, a population, and an area.
 */
public class Country {
    private final String name;
    private final int population;
    private final double area;

    /**
     Constructs a country with a name, a population and an area.
     @param name the name of this country
     @param population the population of this country
     @param area the area of this country
     */
    public Country(String name, int population, double area) {
        this.name = name;
        this.population = population;
        this.area = area;
    }

    /**
     Gets the name of this country.
     @return the name of this country.
     */
    public String getName() {
        return name;
    }

    /**
     Gets the area of this country.
     @return the area of this country.
     */
    public double getArea() {
        return area;
    }

    /**
     Gets the population of this country.
     @return the population of this country.
     */
    public int getPopulation() {
        return population;
    }

    /**
     Gets the population density of this country.
     @return the population density of this country.
     */
    public double getPopulationDensity() {
        return getPopulation()/getArea();
    }
}
