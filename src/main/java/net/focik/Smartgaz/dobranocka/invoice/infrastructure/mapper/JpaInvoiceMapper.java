package net.focik.Smartgaz.dobranocka.invoice.infrastructure.mapper;

import lombok.RequiredArgsConstructor;
import net.focik.Smartgaz.dobranocka.invoice.domain.invoice.Invoice;
import net.focik.Smartgaz.dobranocka.invoice.infrastructure.dto.InvoiceDbDto;
import net.focik.Smartgaz.dobranocka.rent.infrastructure.dto.ReservationDbDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JpaInvoiceMapper {

    private final ModelMapper mapper;

    public Invoice toDomain(InvoiceDbDto dto) {
        Invoice invoice = mapper.map(dto, Invoice.class);
        List<Integer> reservations = dto.getReservations().stream()
                .map(ReservationDbDto::getId)
                .collect(Collectors.toList());
        invoice.setReservationIds(reservations);
        return invoice;
    }
}