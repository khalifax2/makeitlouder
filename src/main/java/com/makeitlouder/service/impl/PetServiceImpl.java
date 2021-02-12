package com.makeitlouder.service.impl;

import com.makeitlouder.domain.Pet;
import com.makeitlouder.domain.PetStatus;
import com.makeitlouder.exceptions.PetServiceException;
import com.makeitlouder.repositories.PetRepository;
import com.makeitlouder.service.PetService;
import com.makeitlouder.shared.dto.PetDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;

    @Override
    public Pet findByName(String name) throws PetServiceException {
        Optional<Pet> foundPet = petRepository.findByName(name);

        if (foundPet.isEmpty()) throw new PetServiceException("Pet with name "+ name +" not found!");

        return foundPet.get();
    }

    @Override
    public Pet findPetById(Long id) throws PetServiceException {
        Optional<Pet> foundPet = petRepository.findById(id);

        if (foundPet.isEmpty()) throw new PetServiceException("Pet not found!");

        return foundPet.get();
    }

    @Override
    public Pet createPet(Pet pet) throws PetServiceException {
        Optional<Pet> foundPet = petRepository.findByName(pet.getName());

        if (foundPet.isPresent()) throw new PetServiceException("Pet already exists!");

        pet.setPetStatus(PetStatus.builder().name("AVAILABLE").build());
        Pet createdPet = petRepository.save(pet);

        return createdPet;
    }

    @Override
    public Pet updatePet(Long id, Pet pet) throws PetServiceException {
        Optional<Pet> foundPet = petRepository.findById(id);

        if (foundPet.isEmpty()) throw new PetServiceException("Pet not found!");

        return petRepository.save(pet);
    }

    @Override
    public void deletePet(Long id) throws PetServiceException {
        Optional<Pet> foundPet = petRepository.findById(id);

        if (foundPet.isEmpty()) throw new PetServiceException("Pet not found!");

        petRepository.delete(foundPet.get());
    }
}
