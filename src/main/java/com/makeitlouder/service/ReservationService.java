package com.makeitlouder.service;

import com.makeitlouder.domain.Reservation;
import com.makeitlouder.shared.dto.ReservationCount;
import com.makeitlouder.shared.dto.ReservationDto;
import com.makeitlouder.shared.dto.ReservedPetDto;

import java.util.List;
import java.util.UUID;


public interface ReservationService {
    ReservationDto createReservation(ReservationDto reservation);
    ReservationDto getReservation(UUID id);
    ReservationDto updateReservation(UUID id, ReservationDto reservation);
    List<ReservationDto> getReservations(int page, int limit);
    void deleteReservation(UUID id);
    List<ReservedPetDto> getUserReservation(UUID userId);
    ReservationCount getReservationCount();
    List<ReservationDto> getReservationForPickupToday(int page, int limit);


}
