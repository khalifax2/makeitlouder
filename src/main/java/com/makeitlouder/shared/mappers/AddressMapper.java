package com.makeitlouder.shared.mappers;

import com.makeitlouder.domain.Address;
import com.makeitlouder.shared.dto.AddressDto;
import com.makeitlouder.ui.model.request.AddressRequestModel;
import org.mapstruct.Mapper;

@Mapper
public interface AddressMapper {

    AddressDto addressRequestToAddressDto(AddressRequestModel addressRequestModel);
    Address addressDtoToAddressEntity(AddressDto addressDto);
}
