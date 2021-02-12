package com.makeitlouder.ui.controllers;

import com.makeitlouder.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController()
@RequestMapping("/pets")
public class ReservationController {

    private final ReservationService reservationService;

  
    
}
