package com.makeitlouder.service.impl;

import com.amazonaws.services.opsworks.model.UserProfile;
import com.makeitlouder.bucket.BucketName;
import com.makeitlouder.domain.Pet;
import com.makeitlouder.domain.enumerated.S3FolderName;
import com.makeitlouder.domain.enumerated.Status;
import com.makeitlouder.exceptions.PetServiceException;
import com.makeitlouder.filestore.FileStore;
import com.makeitlouder.repositories.PetRepository;
import com.makeitlouder.service.PetService;
import com.makeitlouder.shared.dto.PetDto;
import com.makeitlouder.shared.mappers.PetMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.apache.http.entity.ContentType.*;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final FileStore fileStore;
    private final PetMapper petMapper;

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
    public PetDto createPet(PetDto petDto, MultipartFile file) throws PetServiceException {
        Optional<Pet> foundPet = petRepository.findByName(petDto.getName());

        if (foundPet.isPresent()) throw new PetServiceException("Pet already exists!");

        Pet pet = petMapper.PetDtoToPet(petDto);
        pet.setPetStatus(Status.AVAILABLE);

        Pet savedPet = petRepository.save(pet);

        if (file != null) {
            uploadPetProfileImage(file, savedPet);
        }

        PetDto createdPet = petMapper.PetToPetDto(savedPet);

        return createdPet;
    }

    @Override
    public List<PetDto> getPets(int page, int limit) {
        if (page > 0) page -= 1;

        Pageable pageRequest = PageRequest.of(page, limit);
        Page<Pet> petLists = petRepository.findAll(pageRequest);
        List<PetDto> pets = getPetDtos(petLists);

        return pets;
    }

    @Override
    public List<PetDto> getAvailablePets(int page, int limit) {
        if (page > 0) page -= 1;

        Pageable pageRequest = PageRequest.of(page, limit);
        Page<Pet> petLists = petRepository.getAvailablePets(pageRequest);
        List<PetDto> pets = getPetDtos(petLists);

        return pets;
    }

    @Override
    public PetDto updatePet(Long id, PetDto petDto, MultipartFile file) throws PetServiceException {
        Optional<Pet> foundPet = petRepository.findById(id);
        if (foundPet.isEmpty()) throw new PetServiceException("Pet not found!");

        foundPet.get().setName(petDto.getName());
        foundPet.get().setPetType(petDto.getPetType());
        foundPet.get().setGender(petDto.getGender());

        if (file != null) {
            uploadPetProfileImage(id, file);
        }

        Pet savedPet = petRepository.save(foundPet.get());
        PetDto updatedPet = petMapper.PetToPetDto(savedPet);

        return updatedPet;
    }

    @Override
    public void deletePet(Long id) throws PetServiceException {
        Optional<Pet> foundPet = petRepository.findById(id);

        if (foundPet.isEmpty()) throw new PetServiceException("Pet not found!");

        petRepository.delete(foundPet.get());
    }

    private List<PetDto> getPetDtos(Page<Pet> petLists) {
        return petLists.getContent().stream()
                .map(pet -> {
                    PetDto petDto = petMapper.PetToPetDto(pet);
                    return petDto;
                })
                .collect(Collectors.toList());
    }

    public void uploadPetProfileImage(MultipartFile file, Pet pet) {
        // 1. Check if image is not empty
        isFileEmpty(file);

        // 2. If file is an image
        isImage(file);

        // 4. Grab some metadata from file if any
        Map<String, String> metadata = extractMetadata(file);

        // 5. Store the image in s3 and update db link with s3 image link
        String path = String.format("%s/%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), S3FolderName.PETS, pet.getId());
        String filename = String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());

        try {
            fileStore.save(path, filename, Optional.of(metadata), file.getInputStream());
            pet.setImagePath(filename);
            petRepository.save(pet);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public void uploadPetProfileImage(Long petProfileId, MultipartFile file) {
        // 1. Check if image is not empty
        isFileEmpty(file);

        // 2. If file is an image
        isImage(file);

        // 3. The pet exists in our db
        Pet pet = getPetProfileOrThrow(petProfileId);

        // 4. Grab some metadata from file if any
        Map<String, String> metadata = extractMetadata(file);

        // 5. Store the image in s3 and update db link with s3 image link
        String path = String.format("%s/%s/%s", BucketName.PROFILE_IMAGE.getBucketName(),  S3FolderName.PETS, pet.getId());
        String filename = String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());

        try {
            fileStore.save(path, filename, Optional.of(metadata), file.getInputStream());
            pet.setImagePath(filename);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public byte[] downloadPetProfileImage(Long petProfileId) {
        Pet pet = getPetProfileOrThrow(petProfileId);
        String path = String.format("%s/%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), S3FolderName.PETS, pet.getId());

        return pet.getImagePath()
                .map(key -> fileStore.download(path, key))
                .orElse(new byte[0]);
    }

    private Map<String, String> extractMetadata(MultipartFile file) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));

        return metadata;
    }

    private Pet getPetProfileOrThrow(Long petProfileId) {
        return petRepository.findById(petProfileId)
                .stream()
                .filter(petProfile -> petProfile.getId().equals(petProfileId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("Pet profile %s not found", petProfileId)));
    }

    private void isImage(MultipartFile file) {
        if (!Arrays.asList(IMAGE_JPEG.getMimeType(), IMAGE_PNG.getMimeType()).contains(file.getContentType())) {
            throw new IllegalStateException("File must be an image [" + file.getContentType() + "]");
        }
    }

    private void isFileEmpty(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file [ " + file.getSize() + "]");
        }
    }
}
