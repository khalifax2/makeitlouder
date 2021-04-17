package com.makeitlouder.ui.controllers;

import com.makeitlouder.domain.Reservation;
import com.makeitlouder.service.ReservationService;
import com.makeitlouder.service.UserService;
import com.makeitlouder.shared.dto.ReservationDTO;
import com.makeitlouder.shared.dto.ReservedPetDto;
import com.makeitlouder.shared.mappers.ReservationMapper;
import com.makeitlouder.ui.model.response.OperationalStatusModel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController()
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    @PreAuthorize("hasRole('ADMIN') or #id == principal.id")
    @GetMapping("/{id}")
    public ReservationDTO getReservation(@PathVariable UUID id) {
        return reservationService.getReservation(id);
    }

//    @PreAuthorize("hasRole('ADMIN') or #id == principal.id")
    @GetMapping("/profile/{userId}")
    public List<ReservedPetDto> getUserReservation(@PathVariable UUID userId) {
        return reservationService.getUserReservation(userId);
    }

    @PostMapping
    public ReservationDTO createReservation(@RequestBody ReservationDTO reservationDTO) {
        return reservationService.createReservation(reservationDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<ReservationDTO> getReservations(@RequestParam(value = "page") int page,
                                                @RequestParam(value = "limit") int limit) {

        List<ReservationDTO> reservationList =reservationService.getReservations(page, limit);

        return reservationList;
    }

//    @PreAuthorize("hasRole('ADMIN') or #id == principal.id")
    @PutMapping("/{id}")
    public ReservationDTO updateReservation(@PathVariable UUID id, @RequestBody ReservationDTO reservationDTO) {
        return reservationService.updateReservation(id, reservationDTO);
    }

//    @PreAuthorize("hasRole('ADMIN') or #id == principal.id")
    @DeleteMapping("/{id}")
    public OperationalStatusModel deleteReservation(@PathVariable UUID id) {
        reservationService.deleteReservation(id);
        OperationalStatusModel operationalStatusModel = OperationalStatusModel.builder()
                .operationName("DELETE")
                .operationResult("SUCESS").build();

        return operationalStatusModel;
    }
}
