package com.makeitlouder.shared.mappers;

import com.makeitlouder.domain.User;
import com.makeitlouder.shared.dto.UserDto;
import com.makeitlouder.ui.model.request.UserDetailsRequestModel;
import com.makeitlouder.ui.model.response.UserRest;
import org.mapstruct.*;

//unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
@Mapper(uses = { AddressMapper.class, DateMapper.class, RoleMapper.class })
public interface UserMapper {

    UserDto UserDetailsRequestToUserDto(UserDetailsRequestModel userDetails);
    User UserDtoToUserEntity(UserDto userDetails);
    UserDto UserEntityToUserDto(User userDetails);

    @Mapping(source = "id", target = "userId")
    UserRest UserDtoToUserRest(UserDto userRest);


}
