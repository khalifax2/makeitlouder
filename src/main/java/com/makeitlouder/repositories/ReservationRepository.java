package com.makeitlouder.repositories;

import com.makeitlouder.domain.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {

    @Query("SELECT r FROM Reservation r WHERE CURRENT_TIMESTAMP < r.reservationDate")
    List<Reservation> getCurrentReservation();

    @Query("SELECT COUNT(r) FROM Reservation r WHERE CURRENT_DATE < r.reservationDate")
    Integer getCurrentReservationCount();

    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.reservationDate BETWEEN CURRENT_DATE AND CURRENT_DATE + 1")
    Integer getReservationForPickupCount();

    @Query("SELECT r FROM Reservation r WHERE r.reservationDate BETWEEN CURRENT_DATE AND CURRENT_DATE + 1")
    Page<Reservation> getReservationForPickupToday(Pageable page);
}
