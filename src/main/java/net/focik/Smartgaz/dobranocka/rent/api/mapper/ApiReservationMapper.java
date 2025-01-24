package net.focik.Smartgaz.dobranocka.rent.api.mapper;

import lombok.RequiredArgsConstructor;
import net.focik.Smartgaz.dobranocka.customer.domain.exception.CustomerNotValidException;
import net.focik.Smartgaz.dobranocka.rent.api.dto.ReservationDto;
import net.focik.Smartgaz.dobranocka.rent.domain.Reservation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApiReservationMapper {
    private final ModelMapper modelMapper;

    public Reservation toDomain(ReservationDto dto) {
        valid(dto);
        return modelMapper.map(dto, Reservation.class);
    }

    public ReservationDto toDto(Reservation reservation) {
        return modelMapper.map(reservation, ReservationDto.class);
    }

    private void valid(ReservationDto dto) {
        if (dto.getCustomer() == null || dto.getCustomer().getId() <= 0)
            throw new CustomerNotValidException("IdCustomer can't be null.");
    }
}