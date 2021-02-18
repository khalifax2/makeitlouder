package com.makeitlouder.ui.controllers;

import com.makeitlouder.domain.Pet;
import com.makeitlouder.service.PetService;
import com.makeitlouder.ui.model.response.OperationalStatusModel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController()
@RequestMapping("/pets")
public class PetController {

    private final PetService petService;

    @GetMapping("/{id}")
    public Pet findPetById(@PathVariable Long id) {
        Pet foundPet = petService.getPetById(id);
        return foundPet;
    }

    @PostMapping
    public Pet createPet(@RequestBody Pet pet) {
        Pet createdPet = petService.createPet(pet);
        return createdPet;
    }

    @GetMapping
    public List<Pet> getPets(@RequestParam(value = "page") int page,
                             @RequestParam(value = "limit") int limit) {
        List<Pet> petLists = petService.getPets(page, limit);

        return petLists;
    }

    @PutMapping("/{id}")
    public Pet updatePet(@PathVariable Long id, @RequestBody Pet pet) {
        Pet foundPet = petService.updatePet(id, pet);
        return foundPet;
    }

    @DeleteMapping("/{id}")
    public OperationalStatusModel deletePet(@PathVariable Long id) {
        petService.deletePet(id);
        OperationalStatusModel operation = OperationalStatusModel.builder()
                .operationName("DELETE").operationResult("SUCCESS").build();

        return operation;
    }
}
