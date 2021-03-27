package com.makeitlouder.shared.mappers;

import com.makeitlouder.domain.Reservation;
import com.makeitlouder.shared.dto.ReservationDTO;
import org.mapstruct.DecoratedWith;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
        uses = ReservationDateMapper.class)
@DecoratedWith(ReservationMapperDecorator.class)
public interface ReservationMapper {

    Reservation ReservationDTOtoReservation(ReservationDTO reservationDTO);
    @Mapping(source = "id", target = "reservationId")
    @Mapping(source = "pet.id", target = "petId")
    ReservationDTO ReservationToReservationDTO(Reservation reservation);
}
