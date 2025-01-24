package net.focik.Smartgaz.dobranocka.invoice.api.mapper;

import lombok.RequiredArgsConstructor;
import net.focik.Smartgaz.dobranocka.customer.domain.exception.CustomerNotValidException;
import net.focik.Smartgaz.dobranocka.invoice.api.dto.InvoiceDto;
import net.focik.Smartgaz.dobranocka.invoice.api.dto.InvoiceItemDto;
import net.focik.Smartgaz.dobranocka.invoice.api.dto.VatDto;
import net.focik.Smartgaz.dobranocka.invoice.domain.invoice.Invoice;
import net.focik.Smartgaz.dobranocka.invoice.domain.invoice.InvoiceItem;
import net.focik.Smartgaz.utils.share.Vat;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApiInvoiceMapper {

    private final ModelMapper mapper;

    public Invoice toDomain(InvoiceDto dto) {
        valid(dto);
        Invoice invoice = mapper.map(dto, Invoice.class);

        for (InvoiceItemDto itemDto : dto.getInvoiceItems()) {
            for (InvoiceItem item : invoice.getInvoiceItems()) {
                if (item.getId() == itemDto.getId()) {
                    item.setVat(Vat.valueOf(itemDto.getVat().toString()));
                }
            }
        }
        return invoice;
    }

    public InvoiceDto toDto(Invoice invoice) {
        InvoiceDto dto = mapper.map(invoice, InvoiceDto.class);
        for (InvoiceItem invoiceItem : invoice.getInvoiceItems()) {
            for (InvoiceItemDto itemDto : dto.getInvoiceItems()) {
                if (invoiceItem.getId() == itemDto.getId()) {
                    itemDto.setVat(new VatDto(invoiceItem.getVat().getViewValue(), invoiceItem.getVat().getNumberValue(), invoiceItem.getVat().getMultiplier()));
                }
            }
        }
        return dto;
    }

    private void valid(InvoiceDto dto) {
        if (dto.getCustomer() == null)
            throw new CustomerNotValidException("Customer can't be null.");
//        if (dto.getInvoiceDate().isEmpty())
//            throw new InvoiceNotValidException("Date can't be empty.");
    }
}