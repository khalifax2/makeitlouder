package com.makeitlouder.ui.model.request;

import com.makeitlouder.domain.Address;
import com.makeitlouder.shared.dto.AddressDto;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetailsRequestModel {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private AddressRequestModel address;
    private boolean admin;
}
