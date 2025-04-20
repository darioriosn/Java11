package com.example.petmanagement;

import com.example.petmanagement.entities.Pet;
import com.example.petmanagement.services.PetService;
import com.example.petmanagement.validators.SpeciesCheck;
import com.example.petmanagement.validators.ValidPet;
import com.example.petmanagement.validators.AgeRange;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PetManagementSystem {
    public static void main(String[] args) {
        // Create a list of pets
        List<Pet> pets = Arrays.asList(
                new Pet("Max", "Dog", "Golden Retriever", LocalDate.of(2018, 5, 15), 28.5, true),
                new Pet("Bella", "Cat", "Siamese", LocalDate.of(2019, 3, 10), 4.2, true),
                new Pet("Charlie", "Dog", "Beagle", LocalDate.of(2017, 11, 5), 12.8, false),
                new Pet("Luna", "Cat", "Persian", LocalDate.of(2020, 1, 25), 3.8, true),
                new Pet("Cooper", "Dog", "German Shepherd", LocalDate.of(2016, 8, 12), 32.1, true),
                new Pet("Lucy", "Dog", "Labrador", LocalDate.of(2019, 7, 8), 25.6, true),
                new Pet("Oliver", "Cat", "Maine Coon", LocalDate.of(2018, 2, 20), 7.5, false),
                new Pet("Daisy", "Rabbit", "Holland Lop", LocalDate.of(2020, 6, 3), 1.2, true),
                new Pet("Rocky", "Dog", "Bulldog", LocalDate.of(2015, 12, 1), 22.3, false),
                new Pet("Milo", "Bird", "Parrot", LocalDate.of(2017, 4, 17), 0.4, true)
        );

        System.out.println("=== Basic Usage of var in Lambda Parameters with Pets ===");

        // Example 1: Basic usage with a single parameter
        Consumer<Pet> petPrinter = (var pet) ->
                System.out.println(pet.getName() + " is a " + pet.getBreed() + " " + pet.getSpecies());

        System.out.println("All pets:");
        pets.forEach(petPrinter);

        // Example 2: Using var with multiple parameters
        BiFunction<Pet, Integer, String> petAgeDescription = (var pet, var yearsAhead) -> {
            int currentAge = pet.getAgeInYears();
            return pet.getName() + " is currently " + currentAge + " years old and will be "
                    + (currentAge + yearsAhead) + " years old in " + yearsAhead + " years.";
        };

        System.out.println("\nAge descriptions:");
        pets.stream()
                .limit(3)
                .map(pet -> petAgeDescription.apply(pet, 2))
                .forEach(System.out::println);

        System.out.println("\n=== Using Annotations with var in Lambda Parameters ===");

        // Example 3: Using annotations with var for validation
        System.out.println("Healthy pets:");
        pets.stream()
                .filter((@ValidPet var pet) -> pet.isHealthy())
                .map(pet -> pet.getName() + " (" + pet.getSpecies() + ")")
                .forEach(System.out::println);

        // Example 4: Multiple annotations for complex validation
        Function<Pet, String> petAgeValidator = (@ValidPet @AgeRange(min=1, max=15) var pet) -> {
            int age = pet.getAgeInYears();
            if (age < 1) return pet.getName() + " is too young for this treatment";
            if (age > 15) return pet.getName() + " is too old for this treatment";
            return pet.getName() + " is eligible for the treatment";
        };

        System.out.println("\nTreatment eligibility:");
        pets.forEach(pet -> System.out.println(petAgeValidator.apply(pet)));

        System.out.println("\n=== Complex Data Processing with var in Lambda Parameters ===");

        // Example 5: Complex data processing with var
        Map<String, Double> averageWeightBySpecies = pets.stream()
                .collect(Collectors.groupingBy(
                        (var pet) -> pet.getSpecies(),
                        Collectors.averagingDouble((var pet) -> pet.getWeight())
                ));

        System.out.println("Average weight by species:");
        averageWeightBySpecies.forEach((species, avgWeight) ->
                System.out.println(species + ": " + String.format("%.2f", avgWeight) + " kg"));

        // Example 6: Finding the oldest pet of each species
        Map<String, Pet> oldestPetBySpecies = pets.stream()
                .collect(Collectors.toMap(
                        (var pet) -> pet.getSpecies(),
                        Function.identity(),
                        (var pet1, var pet2) -> pet1.getBirthDate().isBefore(pet2.getBirthDate()) ? pet1 : pet2
                ));

        System.out.println("\nOldest pet of each species:");
        oldestPetBySpecies.forEach((species, pet) ->
                System.out.println(species + ": " + pet.getName() + " (" + pet.getAgeInYears() + " years)"));

        System.out.println("\n=== Custom Pet Service with Annotated Lambda Parameters ===");

        // Example 7: Creating a pet service with annotated lambda parameters
        PetService petService = new PetService();

        // Register handlers using var with annotations
        petService.registerVaccinationHandler((@ValidPet @AgeRange(min=2) var pet) -> {
            if (!pet.isHealthy()) {
                return "Cannot vaccinate " + pet.getName() + " due to health issues";
            }
            return pet.getName() + " has been vaccinated successfully";
        });

        petService.registerGroomingHandler((@ValidPet @SpeciesCheck var pet) -> {
            if (pet.getSpecies().equals("Bird") || pet.getSpecies().equals("Rabbit")) {
                return pet.getName() + " requires special grooming procedures";
            }
            return pet.getName() + " has been groomed using standard procedures";
        });

        // Test the service
        System.out.println("Vaccination results:");
        pets.stream()
                .limit(5)
                .forEach(pet -> System.out.println(petService.vaccinatePet(pet)));

        System.out.println("\nGrooming results:");
        pets.stream()
                .filter(pet -> Arrays.asList("Dog", "Cat", "Rabbit", "Bird").contains(pet.getSpecies()))
                .forEach(pet -> System.out.println(petService.groomPet(pet)));

        System.out.println("\n=== Advanced Filtering and Sorting with var ===");

        // Example 8: Advanced filtering and sorting with var
        List<Pet> filteredAndSortedPets = pets.stream()
                .filter((var pet) -> {
                    // Complex filtering logic
                    boolean isCommonPet = pet.getSpecies().equals("Dog") || pet.getSpecies().equals("Cat");
                    boolean isYoungEnough = pet.getAgeInYears() < 5;
                    boolean isHealthy = pet.isHealthy();
                    return isCommonPet && isYoungEnough && isHealthy;
                })
                .sorted(Comparator.comparing((var pet) -> pet.getWeight()))
                .collect(Collectors.toList());

        System.out.println("Young, healthy dogs and cats (sorted by weight):");
        filteredAndSortedPets.forEach(pet ->
                System.out.println(pet.getName() + " - " + pet.getSpecies() + " - " +
                        String.format("%.1f", pet.getWeight()) + " kg"));

        // Example 9: Creating pet recommendations with var
        Function<String, List<Pet>> petRecommender = (@SpeciesCheck var preferredSpecies) -> {
            return pets.stream()
                    .filter((var pet) -> pet.getSpecies().equals(preferredSpecies) && pet.isHealthy())
                    .sorted(Comparator.comparing((var pet) -> pet.getAgeInYears()))
                    .collect(Collectors.toList());
        };

        System.out.println("\nRecommended cats for adoption (youngest first):");
        List<Pet> recommendedCats = petRecommender.apply("Cat");
        recommendedCats.forEach(pet ->
                System.out.println(pet.getName() + " - " + pet.getBreed() + " - " +
                        pet.getAgeInYears() + " years old"));
    }




}
