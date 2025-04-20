package com.example.petmanagement.entities;

import java.time.LocalDate;
import java.time.Period;

// Pet class to represent pets in our system
public class Pet {
    private String name;
    private String species;
    private String breed;
    private LocalDate birthDate;
    private double weight;
    private boolean healthy;

    public Pet(String name, String species, String breed, LocalDate birthDate,
               double weight, boolean healthy) {
        this.name = name;
        this.species = species;
        this.breed = breed;
        this.birthDate = birthDate;
        this.weight = weight;
        this.healthy = healthy;
    }

    public String getName() { return name; }
    public String getSpecies() { return species; }
    public String getBreed() { return breed; }
    public LocalDate getBirthDate() { return birthDate; }
    public double getWeight() { return weight; }
    public boolean isHealthy() { return healthy; }

    public int getAgeInYears() {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}