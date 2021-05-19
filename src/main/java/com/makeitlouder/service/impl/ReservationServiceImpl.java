package com.makeitlouder.service.impl;

import com.makeitlouder.domain.Reservation;
import com.makeitlouder.domain.User;
import com.makeitlouder.domain.enumerated.Status;
import com.makeitlouder.exceptions.ReservationServiceException;
import com.makeitlouder.repositories.PetRepository;
import com.makeitlouder.repositories.ReservationRepository;
import com.makeitlouder.repositories.UserRepository;
import com.makeitlouder.service.ReservationService;
import com.makeitlouder.shared.dto.ReservationCount;
import com.makeitlouder.shared.dto.ReservationDto;
import com.makeitlouder.shared.dto.ReservedPetDto;
import com.makeitlouder.shared.mappers.ReservationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final UserRepository userRepository;
    private final PetRepository petRepository;


    @Override
    public ReservationDto getReservation(UUID id) throws ReservationServiceException {
        Optional<Reservation> foundReservation = reservationRepository.findById(id);

        if (foundReservation.isEmpty()) throw new ReservationServiceException("Reservation not found!");

        ReservationDto reservation = reservationMapper.ReservationToReservationDto(foundReservation.get());

        return reservation;
    }

    @Override
    public List<ReservedPetDto> getUserReservation(UUID userId) {
        Optional<User> foundReservation = userRepository.findById(userId);

        if (foundReservation.isEmpty()) throw new ReservationServiceException("Reservation not found!");

        List<Reservation> reservationList = foundReservation.get().getReservation();

        List<ReservedPetDto> reservation = reservationList.stream()
                .map(r -> reservationMapper.ReservationToReservedPetDto(r)).collect(Collectors.toList());

        return reservation;
    }

    @Override
    public ReservationDto createReservation(ReservationDto reservationDTO) throws ReservationServiceException {
        Reservation reservation = reservationMapper.ReservationDtoToReservation(reservationDTO);
        Reservation createdReservation = reservationRepository.save(reservation);
        ReservationDto savedReservation = reservationMapper.ReservationToReservationDto(createdReservation);

        return savedReservation;
    }

    @Override
    public List<ReservationDto> getReservations(int page, int limit) {
        if (page > 0) page -= 1;

        Pageable pageableRequest = PageRequest.of(page, limit);

        Page<Reservation> reservationsList = reservationRepository.findAll(pageableRequest);
        List<Reservation> reservations = reservationsList.getContent();

        List<ReservationDto> reservationDTOList = new ArrayList<>();

        for (Reservation reservation : reservations) {
            ReservationDto reservationDTO = reservationMapper.ReservationToReservationDto(reservation);
            reservationDTOList.add(reservationDTO);
        }

        return reservationDTOList;
    }

    @Override
    public List<ReservationDto> getReservationForPickupToday(int page, int limit) {
        if (page > 0) page -= 1;

        Pageable pageableRequest = PageRequest.of(page, limit);

        Page<Reservation> reservationsList = reservationRepository.getReservationForPickupToday(pageableRequest);
        List<Reservation> reservations = reservationsList.getContent();

        List<ReservationDto> reservationDto = reservations.stream()
                .map(r -> reservationMapper.ReservationToReservationDto(r)).collect(Collectors.toList());

        return reservationDto;
    }

    @Override
    public ReservationDto updateReservation(UUID id, ReservationDto reservationDTO) throws ReservationServiceException{
        Optional<Reservation> foundReservation = reservationRepository.findById(id);

        if (foundReservation.isEmpty()) throw new ReservationServiceException("Reservation not found!");

        foundReservation.get().setReservationDate(reservationDTO.getReservationDate());

        Reservation updatedReservation = reservationRepository.save(foundReservation.get());
        ReservationDto savedReservation = reservationMapper.ReservationToReservationDto(updatedReservation);

        return savedReservation;
    }

    @Override
    public void deleteReservation(UUID id) throws ReservationServiceException{
        Optional<Reservation> foundReservation = reservationRepository.findById(id);

        if (foundReservation.isEmpty()) throw new ReservationServiceException("Reservation not found!");

        foundReservation.get().getPet().setPetStatus(Status.AVAILABLE);

        reservationRepository.delete(foundReservation.get());
    }

    @Override
    public ReservationCount getReservationCount() {
        Integer currentReservationCount = reservationRepository.getCurrentReservationCount();
        Integer petCount = petRepository.getAvailablePetsCount();
        Integer forPickupCount = reservationRepository.getReservationForPickupCount();

        ReservationCount count = ReservationCount.builder()
                .reservations(currentReservationCount)
                .forPickup(forPickupCount)
                .availablePet(petCount).build();

        return count;
    }


}
