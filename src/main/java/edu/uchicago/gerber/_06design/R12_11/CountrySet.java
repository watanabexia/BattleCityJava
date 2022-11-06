package edu.uchicago.gerber._06design.R12_11;

import java.util.ArrayList;

/**
  A set of countries.
 */
public class CountrySet {
    private ArrayList<Country> set;

    public CountrySet() {}

    /**
     * Add a country to the set.
     * @param country the country to be added
     * */
    public void add_country(Country country) {}

    /**
     * Get the country with the largest area in the set.
     * @return the country with the largest area in the set, a country
     * with an empty name if the set is empty.
     * */
    public Country get_max_area() {return new Country("",0,0);}

    /**
     * Get the country with the largest population in the set.
     * @return the country with the largest population in the set, a country
     * with an empty name if the set is empty.
     * */
    public Country get_max_population() {return new Country("",0,0);}

    /**
     * Get the country with the largest population density in the set.
     * @return the country with the largest population density in the set, a country
     * with an empty name if the set is empty.
     * */
    public Country get_max_population_density() {return new Country("",0,0);}
}
