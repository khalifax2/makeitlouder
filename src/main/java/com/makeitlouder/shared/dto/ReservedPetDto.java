package com.makeitlouder.shared.dto;

import com.makeitlouder.domain.Pet;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservedPetDto {
    private UUID reservationId;
    private Timestamp reservationDate;
    private Pet pet;
    private UUID userId;
}
