package com.makeitlouder.service;

import com.makeitlouder.domain.Pet;
import com.makeitlouder.shared.dto.PetDto;

import java.util.Optional;

public interface PetService {
    Pet findByName(String name);
    Pet findPetById(Long id);
    Pet createPet(Pet pet);
    Pet updatePet(Long id, Pet pet);
    void deletePet(Long id);
}
