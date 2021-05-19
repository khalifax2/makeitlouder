package com.makeitlouder.shared.dto;

import com.makeitlouder.domain.Pet;
import com.makeitlouder.domain.User;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationDto {
    private UUID reservationId;
    private Timestamp reservationDate;
    private UUID userId;
    private Long petId;
}
