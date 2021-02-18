package com.makeitlouder.shared.mappers;

import com.makeitlouder.domain.Pet;
import com.makeitlouder.domain.PetStatus;
import com.makeitlouder.domain.Reservation;
import com.makeitlouder.domain.User;
import com.makeitlouder.exceptions.ReservationServiceException;
import com.makeitlouder.repositories.PetRepository;
import com.makeitlouder.repositories.PetStatusRepository;
import com.makeitlouder.repositories.UserRepository;
import com.makeitlouder.shared.dto.ReservationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Arrays;

public abstract class ReservationMapperDecorator implements ReservationMapper {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private PetStatusRepository petStatusRepository;

    @Autowired
    @Qualifier("delegate")
    private ReservationMapper reservationMapper;

    @Override
    public Reservation ReservationDTOtoReservation(ReservationDTO reservationDTO) throws ReservationServiceException {
        Reservation reservation = reservationMapper.ReservationDTOtoReservation(reservationDTO);

        User foundUser = userRepository.findById(reservationDTO.getUserId()).get();
        Pet foundPet = petRepository.findById(reservationDTO.getPetId()).get();

        if (foundPet.getPetStatus().getName().equals("RESERVED"))
            throw new ReservationServiceException("Pet was already reserved!");

        reservation.setUser(Arrays.asList(foundUser));
        reservation.setPet(foundPet);
        foundPet.setPetStatus(petStatusRepository.findByName("RESERVED"));
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
