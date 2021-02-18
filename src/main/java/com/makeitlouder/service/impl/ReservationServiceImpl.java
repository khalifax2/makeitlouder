package com.makeitlouder.service.impl;

import com.makeitlouder.domain.Reservation;
import com.makeitlouder.exceptions.ReservationServiceException;
import com.makeitlouder.repositories.ReservationRepository;
import com.makeitlouder.service.ReservationService;
import com.makeitlouder.shared.dto.ReservationDTO;
import com.makeitlouder.shared.mappers.ReservationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;

    @Override
    public ReservationDTO getReservation(UUID id) throws ReservationServiceException {
        Optional<Reservation> foundReservation = reservationRepository.findById(id);

        if (foundReservation.isEmpty()) throw new ReservationServiceException("Reservation not found!");

        ReservationDTO reservation = reservationMapper.ReservationToReservationDTO(foundReservation.get());

        return reservation;
    }

    @Override
    public ReservationDTO createReservation(ReservationDTO reservationDTO) throws ReservationServiceException {
        Reservation reservation = reservationMapper.ReservationDTOtoReservation(reservationDTO);
        Reservation createdReservation = reservationRepository.save(reservation);
        ReservationDTO savedReservation = reservationMapper.ReservationToReservationDTO(createdReservation);

        return savedReservation;
    }

    @Override
    public List<ReservationDTO> getReservations(int page, int limit) {
        if (page > 0) page -= 1;

        Pageable pageableRequest = PageRequest.of(page, limit);

        Page<Reservation> reservationsList = reservationRepository.findAll(pageableRequest);
        List<Reservation> reservations = reservationsList.getContent();

        List<ReservationDTO> reservationDTOList = new ArrayList<>();

        for (Reservation reservation : reservations) {
            ReservationDTO reservationDTO = reservationMapper.ReservationToReservationDTO(reservation);
            reservationDTOList.add(reservationDTO);
        }

        return reservationDTOList;
    }

    @Override
    public ReservationDTO updateReservation(UUID id, ReservationDTO reservationDTO) throws ReservationServiceException{
        Optional<Reservation> foundReservation = reservationRepository.findById(id);

        if (foundReservation.isEmpty()) throw new ReservationServiceException("Reservation not found!");

        Reservation reservation = reservationMapper.ReservationDTOtoReservation(reservationDTO);
        Reservation updatedReservation = reservationRepository.save(reservation);
        ReservationDTO savedReservation = reservationMapper.ReservationToReservationDTO(updatedReservation);

        return savedReservation;
    }

    @Override
    public void deleteReservation(UUID id) throws ReservationServiceException{
        Optional<Reservation> foundReservation = reservationRepository.findById(id);

        if (foundReservation.isEmpty()) throw new ReservationServiceException("Reservation not found!");

        reservationRepository.delete(foundReservation.get());
    }
}
