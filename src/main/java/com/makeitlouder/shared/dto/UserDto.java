package com.makeitlouder.shared.dto;

import com.makeitlouder.domain.Address;
import com.makeitlouder.ui.model.request.AddressRequestModel;
import lombok.*;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto extends BaseDto {

    private static final long serialVersionUID = 9114367160829984995L;

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String encryptedPassword;
    private boolean emailVerificationToken = false;
    private AddressDto address;
    private Set<String> roles;

}
