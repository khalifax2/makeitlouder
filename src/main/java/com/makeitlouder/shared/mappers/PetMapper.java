package com.makeitlouder.shared.mappers;

import com.makeitlouder.domain.Pet;
import com.makeitlouder.shared.dto.PetDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { DateMapper.class },
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
@DecoratedWith(PetMapperDecorator.class)
public interface PetMapper {

    Pet PetDtoToPet(PetDto petDto);
    PetDto PetToPetDto(Pet pet);
}
