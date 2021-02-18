package com.makeitlouder.ui.controllers;

import com.makeitlouder.domain.Reservation;
import com.makeitlouder.service.ReservationService;
import com.makeitlouder.shared.dto.ReservationDTO;
import com.makeitlouder.shared.mappers.ReservationMapper;
import com.makeitlouder.ui.model.response.OperationalStatusModel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController()
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("/{id}")
    public ReservationDTO getReservation(@PathVariable UUID id) {
        return reservationService.getReservation(id);
    }

    @PostMapping
    public ReservationDTO createReservation(@RequestBody ReservationDTO reservationDTO) {
        return reservationService.createReservation(reservationDTO);
    }

    @GetMapping
    public List<ReservationDTO> getReservations(@RequestParam(value = "page") int page,
                                                @RequestParam(value = "limit") int limit) {

        List<ReservationDTO> reservationList =reservationService.getReservations(page, limit);

        return reservationList;
    }

    @PutMapping("/{id}")
    public ReservationDTO updateReservation(@PathVariable UUID id, @RequestBody ReservationDTO reservationDTO) {
        return reservationService.updateReservation(id, reservationDTO);
    }

    @DeleteMapping("/{id}")
    public OperationalStatusModel deleteReservation(@PathVariable UUID id) {
        reservationService.deleteReservation(id);
        OperationalStatusModel operationalStatusModel = OperationalStatusModel.builder()
                .operationName("DELETE")
                .operationResult("SUCESS").build();

        return operationalStatusModel;
    }
}
