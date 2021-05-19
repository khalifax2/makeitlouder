package com.makeitlouder.service;

import com.makeitlouder.domain.Pet;
import com.makeitlouder.shared.dto.PetDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PetService {
    Pet findByName(String name);
    Pet getPetById(Long id);
    PetDto createPet(PetDto pet, MultipartFile file);
    PetDto updatePet(Long id, PetDto petDto, MultipartFile file);
    List<PetDto> getPets(int page, int limit);
    List<PetDto> getAvailablePets(int page, int limit);
    void deletePet(Long id);
    void uploadPetProfileImage(Long petProfileId, MultipartFile file);
    byte[] downloadPetProfileImage(Long petProfileId);
}
