package com.makeitlouder.ui.controllers;

import com.makeitlouder.service.ReservationService;
import com.makeitlouder.shared.dto.ReservationCount;
import com.makeitlouder.shared.dto.ReservationDto;
import com.makeitlouder.shared.dto.ReservedPetDto;
import com.makeitlouder.ui.model.response.OperationalStatusModel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController()
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    @PostAuthorize("hasRole('ADMIN') or returnObject.id == principal.id")
    @GetMapping("/{id}")
    public ReservationDto getReservation(@PathVariable UUID id) {
        return reservationService.getReservation(id);
    }

    @PreAuthorize("hasRole('ADMIN') or #userId == principal.id")
    @GetMapping("/profile/{userId}")
    public List<ReservedPetDto> getUserReservation(@PathVariable UUID userId) {
        return reservationService.getUserReservation(userId);
    }

    @PostMapping
    public ReservationDto createReservation(@RequestBody ReservationDto reservationDTO) {
        return reservationService.createReservation(reservationDTO);
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<ReservationDto> getReservations(@RequestParam(value = "page") int page,
                                                @RequestParam(value = "limit") int limit) {

        List<ReservationDto> reservationList =reservationService.getReservations(page, limit);

        return reservationList;
    }

    @PreAuthorize("hasRole('ADMIN') or #reservationDTO.userId == principal.id")
    @PutMapping("/{id}")
    public ReservationDto updateReservation(@PathVariable UUID id, @RequestBody ReservationDto reservationDTO) {
        return reservationService.updateReservation(id, reservationDTO);
    }

//    @PreAuthorize("hasRole('ADMIN') or #userId == principal.id")
    @DeleteMapping("/{reservationId}/user/{userId}")
    public OperationalStatusModel deleteReservation(@PathVariable UUID userId, @PathVariable UUID reservationId) {
        reservationService.deleteReservation(reservationId);
        OperationalStatusModel operationalStatusModel = OperationalStatusModel.builder()
                .operationName("DELETE")
                .operationResult("SUCESS").build();

        return operationalStatusModel;
    }

    @GetMapping("/count")
    public ReservationCount reservationCount() {
        return reservationService.getReservationCount();
    }

    @GetMapping("/pickup-today")
    public List<ReservationDto> getReservationForPickupToday(@RequestParam(value = "page") int page,
                                                             @RequestParam(value = "limit") int limit) {
        return reservationService.getReservationForPickupToday(page, limit);
    }
}
