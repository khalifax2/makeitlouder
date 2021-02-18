package com.makeitlouder.service;

import com.makeitlouder.domain.Pet;

import java.util.List;

public interface PetService {
    Pet findByName(String name);
    Pet getPetById(Long id);
    Pet createPet(Pet pet);
    Pet updatePet(Long id, Pet pet);
    List<Pet> getPets(int page, int limit);
    void deletePet(Long id);
}
