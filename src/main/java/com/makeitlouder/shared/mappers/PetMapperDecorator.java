package com.makeitlouder.shared.mappers;

import com.makeitlouder.domain.Pet;
import com.makeitlouder.repositories.PetRepository;
import com.makeitlouder.shared.dto.PetDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class PetMapperDecorator implements PetMapper {

    @Autowired
    private PetRepository petRepository;


    @Autowired
    @Qualifier("delegate")
    private PetMapper petMapper;

    @Override
    public PetDto PetToPetDto(Pet pet) {
        PetDto petDto = petMapper.PetToPetDto(pet);

        Pet foundPet = petRepository.findById(pet.getId()).get();

        if (foundPet.getImagePath().isPresent()) {
            petDto.setImageLink(true);
        }

        return petDto;
    }
}
