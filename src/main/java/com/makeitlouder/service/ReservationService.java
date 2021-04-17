package com.makeitlouder.service;

import com.makeitlouder.domain.Reservation;
import com.makeitlouder.shared.dto.ReservationDTO;
import com.makeitlouder.shared.dto.ReservedPetDto;
import lombok.Setter;

import java.util.List;
import java.util.UUID;


public interface ReservationService {
    ReservationDTO createReservation(ReservationDTO reservation);
    ReservationDTO getReservation(UUID id);
    ReservationDTO updateReservation(UUID id, ReservationDTO reservation);
    List<ReservationDTO> getReservations(int page, int param);
    void deleteReservation(UUID id);
    List<ReservedPetDto> getUserReservation(UUID userId);


}
