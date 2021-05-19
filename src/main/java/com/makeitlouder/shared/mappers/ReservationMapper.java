package com.makeitlouder.shared.mappers;

import com.makeitlouder.domain.Reservation;
import com.makeitlouder.shared.dto.ReservationDto;
import com.makeitlouder.shared.dto.ReservedPetDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
@DecoratedWith(ReservationMapperDecorator.class)
public interface ReservationMapper {

    Reservation ReservationDtoToReservation(ReservationDto reservationDTO);
    @Mapping(source = "id", target = "reservationId")
    @Mapping(source = "pet.id", target = "petId")
    ReservationDto ReservationToReservationDto(Reservation reservation);
    @Mapping(source = "id", target = "reservationId")
    ReservedPetDto ReservationToReservedPetDto(Reservation reservation);
}

