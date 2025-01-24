package net.focik.Smartgaz.dobranocka.invoice.infrastructure.jpa;

import net.focik.Smartgaz.dobranocka.invoice.infrastructure.dto.InvoiceItemDbDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface InvoiceItemDtoRepository extends JpaRepository<InvoiceItemDbDto, Long> {

}
