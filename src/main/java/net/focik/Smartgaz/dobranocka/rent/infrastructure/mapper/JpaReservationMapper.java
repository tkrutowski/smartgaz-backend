package net.focik.Smartgaz.dobranocka.rent.infrastructure.mapper;

import lombok.RequiredArgsConstructor;
import net.focik.Smartgaz.dobranocka.invoice.infrastructure.dto.InvoiceDbDto;
import net.focik.Smartgaz.dobranocka.rent.domain.Reservation;
import net.focik.Smartgaz.dobranocka.rent.infrastructure.dto.ReservationDbDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JpaReservationMapper {

    private final ModelMapper mapper;

    public Reservation toDomain(ReservationDbDto dto) {
        Reservation reservation = mapper.map(dto, Reservation.class);
        reservation.setInvoiceId(Optional.ofNullable(dto.getInvoice())
                .map(InvoiceDbDto::getIdInvoice)
                .orElse(null));
        return reservation;
    }
}