package com.makeitlouder.shared.mappers;

import com.makeitlouder.domain.*;
import com.makeitlouder.domain.enumerated.Status;
import com.makeitlouder.exceptions.ReservationServiceException;
import com.makeitlouder.repositories.PetRepository;
import com.makeitlouder.repositories.UserRepository;
import com.makeitlouder.shared.dto.ReservationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Arrays;

public abstract class ReservationMapperDecorator implements ReservationMapper {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PetRepository petRepository;


    @Autowired
    @Qualifier("delegate")
    private ReservationMapper reservationMapper;

    @Override
    public Reservation ReservationDTOtoReservation(ReservationDTO reservationDTO) throws ReservationServiceException {
        Reservation reservation = reservationMapper.ReservationDTOtoReservation(reservationDTO);

        User foundUser = userRepository.findById(reservationDTO.getUserId()).get();
        Pet foundPet = petRepository.findById(reservationDTO.getPetId()).get();

        if (foundPet.getPetStatus().equals(Status.RESERVED))
            throw new ReservationServiceException("Pet was already reserved!");

        reservation.setUser(Arrays.asList(foundUser));
        reservation.setPet(foundPet);
        foundPet.setPetStatus(Status.RESERVED);
        reservation.setReservationDate(reservationDTO.getReservationDate());

        return reservation;
    }

    @Override
    public ReservationDTO ReservationToReservationDTO(Reservation reservation) {
        ReservationDTO reservationDTO = reservationMapper.ReservationToReservationDTO(reservation);
        reservationDTO.setUserId(reservation.getUser().get(0).getId());

        return reservationDTO;
    }
}
