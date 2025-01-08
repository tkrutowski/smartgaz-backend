package net.focik.Smartgaz.dobranocka.invoice.infrastructure.jpa;

import lombok.AllArgsConstructor;
import net.focik.Smartgaz.dobranocka.invoice.domain.invoice.Invoice;
import net.focik.Smartgaz.dobranocka.invoice.domain.invoice.InvoiceItem;
import net.focik.Smartgaz.dobranocka.invoice.domain.invoice.port.secondary.InvoiceRepository;
import net.focik.Smartgaz.dobranocka.invoice.infrastructure.dto.InvoiceDbDto;
import net.focik.Smartgaz.dobranocka.invoice.infrastructure.dto.InvoiceItemDbDto;
import net.focik.Smartgaz.dobranocka.invoice.infrastructure.mapper.JpaInvoiceMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class InvoiceRepositoryAdapter implements InvoiceRepository {

    private final InvoiceDtoRepository invoiceDtoRepository;
    private final InvoiceItemDtoRepository invoiceItemDtoRepository;
    private final JpaInvoiceMapper mapper;

    @Override
    public Invoice save(Invoice invoice) {
        List<InvoiceItem> invoiceItems = invoice.getInvoiceItems();
        InvoiceDbDto dbDto = mapper.toDto(invoice);
        InvoiceDbDto saved = invoiceDtoRepository.save(dbDto);
        invoiceItems.forEach(item -> item.setIdInvoice(saved.getIdInvoice()));

        List<InvoiceItemDbDto> dtoList = invoiceItems.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
        invoiceItemDtoRepository.saveAll(dtoList);

        return mapper.toDomain(saved);
    }

    @Override
    public void deleteInvoice(Integer id) {
        invoiceDtoRepository.deleteById(id);
    }

    @Override
    public List<Invoice> findAll() {
        return invoiceDtoRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Invoice> findById(Integer id) {
        return invoiceDtoRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Invoice> findByNumber(String number) {
        return invoiceDtoRepository.findByNumber(number)
                .map(mapper::toDomain);
    }


    @Override
    public List<InvoiceItem> findByInvoiceId(Integer idInvoice) {
        return invoiceItemDtoRepository.findAllByIdInvoice(idInvoice).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAllInvoiceItemsByInvoiceId(Integer id) {
        invoiceItemDtoRepository.deleteAllByIdInvoice(id);
    }

}
