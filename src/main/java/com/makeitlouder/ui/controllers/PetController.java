package com.makeitlouder.ui.controllers;

import com.makeitlouder.domain.Pet;
import com.makeitlouder.service.PetService;
import com.makeitlouder.shared.dto.PetDto;
import com.makeitlouder.ui.model.response.OperationalStatusModel;
import com.makeitlouder.ui.model.response.PetRest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController()
@RequestMapping("/pets")
public class PetController {

    private final PetService petService;

    @GetMapping("/{id}")
    public Pet findPetById(@PathVariable Long id) {
        Pet foundPet = petService.findPetById(id);
        return foundPet;
    }

    @PostMapping
    public Pet createPet(@RequestBody Pet pet) {
        Pet createdPet = petService.createPet(pet);
        return createdPet;
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
