package com.makeitlouder.ui.controllers;

import com.makeitlouder.domain.Pet;
import com.makeitlouder.service.PetService;
import com.makeitlouder.shared.dto.PetDto;
import com.makeitlouder.ui.model.response.OperationalStatusModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.time.ZoneOffset;
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

//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public PetDto createPet(@RequestPart PetDto petDto, @RequestPart(required = false) MultipartFile file) {
        PetDto createdPet = petService.createPet(petDto, file);
        return createdPet;
    }


    @GetMapping
    public List<PetDto> getPets(@RequestParam(value = "page") int page,
                                @RequestParam(value = "limit") int limit) {
        List<PetDto> petLists = petService.getPets(page, limit);

        return petLists;
    }

    @GetMapping("/available")
    public List<PetDto> getAvailablePets(@RequestParam(value = "page") int page,
                                         @RequestParam(value = "limit") int limit) {
        List<PetDto> petLists = petService.getAvailablePets(page, limit);

        return petLists;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(
            path = "/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public PetDto updatePet(@PathVariable Long id,
                            @RequestPart PetDto petDto,
                            @RequestPart(required = false) MultipartFile file) {
        PetDto updatedPet = petService.updatePet(id, petDto, file);
        return updatedPet;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public OperationalStatusModel deletePet(@PathVariable Long id) {
        petService.deletePet(id);
        OperationalStatusModel operation = OperationalStatusModel.builder()
                .operationName("DELETE").operationResult("SUCCESS").build();

        return operation;
    }

    @PostMapping(
            path = "/{petProfileId}/image/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void uploadPetProfileImage(@PathVariable("petProfileId") Long petProfileId,
                                      @RequestParam("file") MultipartFile file) {
        petService.uploadPetProfileImage(petProfileId,file);
    }

    @GetMapping(path = "/{petProfileId}/image/download")
    public byte[] downloadUserProfileImage(@PathVariable("petProfileId") Long petProfileId) {
        return petService.downloadPetProfileImage(petProfileId);
    }
}
