package com.example.petmanagement.services;

import com.example.petmanagement.entities.Pet;
import java.util.function.Function;


public class PetService {
    private Function<Pet, String> vaccinationHandler;
    private Function<Pet, String> groomingHandler;

    public void registerVaccinationHandler(Function<Pet, String> handler) {
        this.vaccinationHandler = handler;
    }

    public void registerGroomingHandler(Function<Pet, String> handler) {
        this.groomingHandler = handler;
    }

    public String vaccinatePet(Pet pet) {
        if (vaccinationHandler == null) {
            return "No vaccination handler registered";
        }
        return vaccinationHandler.apply(pet);
    }

    public String groomPet(Pet pet) {
        if (groomingHandler == null) {
            return "No grooming handler registered";
        }
        return groomingHandler.apply(pet);
    }
}
