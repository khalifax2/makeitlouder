package com.makeitlouder.ui.model.response;

import com.makeitlouder.domain.Address;
import com.makeitlouder.shared.dto.AddressDto;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRest {
    private UUID userId;
    private String firstName;
    private String lastName;
    private String email;
    private AddressDto address;
}
