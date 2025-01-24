package net.focik.Smartgaz.dobranocka.invoice.infrastructure.jpa;

import net.focik.Smartgaz.dobranocka.invoice.infrastructure.dto.InvoiceDbDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

interface InvoiceDtoRepository extends JpaRepository<InvoiceDbDto, Integer> {

    Optional<InvoiceDbDto> findByNumber(String number);

    List<InvoiceDbDto> findAllByCustomer_Id(Integer customerId);
}
