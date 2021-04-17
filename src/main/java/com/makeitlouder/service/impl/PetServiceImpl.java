package com.makeitlouder.service.impl;

import com.makeitlouder.domain.Pet;
import com.makeitlouder.domain.enumerated.Status;
import com.makeitlouder.exceptions.PetServiceException;
import com.makeitlouder.repositories.PetRepository;
import com.makeitlouder.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public Pet getPetById(Long id) throws PetServiceException {
        Optional<Pet> foundPet = petRepository.findById(id);

        if (foundPet.isEmpty()) throw new PetServiceException("Pet not found!");

        return foundPet.get();
    }

    @Override
    public Pet createPet(Pet pet) throws PetServiceException {
        Optional<Pet> foundPet = petRepository.findByName(pet.getName());

        if (foundPet.isPresent()) throw new PetServiceException("Pet already exists!");

        pet.setPetStatus(Status.AVAILABLE);
        pet.setGender(pet.getGender());

        Pet createdPet = petRepository.save(pet);

        return createdPet;
    }

    @Override
    public List<Pet> getPets(int page, int limit) {

        if (page > 0) page -= 1;

        Pageable pageRequest = PageRequest.of(page, limit);

        Page<Pet> petLists = petRepository.findAll(pageRequest);
        List<Pet> pets = petLists.getContent();

        return pets;
    }

    @Override
    public List<Pet> getAvailablePets(int page, int limit) {

        if (page > 0) page -= 1;

        Pageable pageRequest = PageRequest.of(page, limit);

        Page<Pet> petLists = petRepository.getAvailablePets(pageRequest);
        List<Pet> pets = petLists.getContent();

        return pets;
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
