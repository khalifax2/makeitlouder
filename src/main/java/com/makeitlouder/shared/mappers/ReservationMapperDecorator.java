package com.makeitlouder.shared.mappers;

import com.makeitlouder.domain.*;
import com.makeitlouder.domain.enumerated.Status;
import com.makeitlouder.exceptions.ReservationServiceException;
import com.makeitlouder.repositories.PetRepository;
import com.makeitlouder.repositories.UserRepository;
import com.makeitlouder.shared.dto.ReservationDto;
import com.makeitlouder.shared.dto.ReservedPetDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class ReservationMapperDecorator implements ReservationMapper {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PetRepository petRepository;


    @Autowired
    @Qualifier("delegate")
    private ReservationMapper reservationMapper;

    @Override
    public Reservation ReservationDtoToReservation(ReservationDto reservationDTO) throws ReservationServiceException {
        Reservation reservation = reservationMapper.ReservationDtoToReservation(reservationDTO);

        User foundUser = userRepository.findById(reservationDTO.getUserId()).get();
        Pet foundPet = petRepository.findById(reservationDTO.getPetId()).get();

        if (foundPet.getPetStatus().equals(Status.RESERVED))
            throw new ReservationServiceException("Pet was already reserved!");

        reservation.setUser(foundUser);
        reservation.setPet(foundPet);
        foundPet.setPetStatus(Status.RESERVED);
        reservation.setReservationDate(reservationDTO.getReservationDate());

        return reservation;
    }

    @Override
    public ReservationDto ReservationToReservationDto(Reservation reservation) {
        ReservationDto reservationDTO = reservationMapper.ReservationToReservationDto(reservation);
        reservationDTO.setUserId(reservation.getUser().getId());

        return reservationDTO;
    }

    @Override
    public ReservedPetDto ReservationToReservedPetDto(Reservation reservation) {
        ReservedPetDto reservedPetDTO = reservationMapper.ReservationToReservedPetDto(reservation);
        reservedPetDTO.setPet(reservation.getPet());
        reservedPetDTO.setUserId(reservation.getUser().getId());
        return reservedPetDTO;
    }
}
