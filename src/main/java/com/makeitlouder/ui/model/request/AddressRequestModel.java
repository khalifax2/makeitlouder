package com.makeitlouder.ui.model.request;

import com.makeitlouder.shared.dto.BaseDto;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressRequestModel {
    private String street;
    private String state;
    private String city;
    private String postalCode;
}
