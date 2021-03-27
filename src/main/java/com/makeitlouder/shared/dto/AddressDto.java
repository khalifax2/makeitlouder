package com.makeitlouder.shared.dto;

import lombok.*;
import org.springframework.boot.autoconfigure.batch.BatchDataSource;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDto {
    private String street;
    private String state;
    private String city;
    private String postalCode;
}
