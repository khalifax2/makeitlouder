package com.makeitlouder.shared.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationCount {
    private int reservations;
    private int forPickup;
    private int availablePet;
}
