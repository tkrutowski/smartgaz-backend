package net.focik.Smartgaz.dobranocka.invoice.infrastructure.mapper;

import net.focik.Smartgaz.dobranocka.customer.domain.customer.Customer;
import net.focik.Smartgaz.dobranocka.invoice.domain.invoice.Invoice;
import net.focik.Smartgaz.dobranocka.invoice.domain.invoice.InvoiceItem;
import net.focik.Smartgaz.dobranocka.invoice.infrastructure.dto.InvoiceDbDto;
import net.focik.Smartgaz.dobranocka.invoice.infrastructure.dto.InvoiceItemDbDto;
import org.javamoney.moneta.Money;
import org.javamoney.moneta.spi.MoneyUtils;
import org.springframework.stereotype.Component;

@Component
public class JpaInvoiceMapper {

    public InvoiceDbDto toDto(Invoice i) {
        return InvoiceDbDto.builder()
                .idInvoice(i.getIdInvoice())
                .idCustomer(i.getCustomer().getId())
                .number(i.getInvoiceNumber())
                .paymentMethod(i.getPaymentMethod())
                .sellDate(i.getSellDate())
                .invoiceDate(i.getInvoiceDate())
                .paymentStatus(i.getPaymentStatus())
                .paymentDate(i.getPaymentDate())
                .otherInfo(i.getOtherInfo())
                .build();
    }

    public Invoice toDomain(InvoiceDbDto dto) {
        return Invoice.builder()
                .idInvoice(dto.getIdInvoice())
                .customer(Customer.builder().id(dto.getIdCustomer()).build())
                .paymentMethod(dto.getPaymentMethod())
                .invoiceDate(dto.getInvoiceDate())
                .sellDate(dto.getSellDate())
                .paymentStatus(dto.getPaymentStatus())
                .paymentDate(dto.getPaymentDate())
                .invoiceNumber(dto.getNumber())
                .otherInfo(dto.getOtherInfo())
                .build();
    }

    public InvoiceItemDbDto toDto(InvoiceItem i) {
        return InvoiceItemDbDto.builder()
                .idInvoice(i.getIdInvoice())
                .idInvoiceItem(i.getIdInvoiceItem())
                .name(i.getName())
                .pkwiu(i.getPkwiu())
                .unit(i.getUnit())
                .quantity(i.getQuantity())
                .amount(MoneyUtils.getBigDecimal(i.getAmount().getNumber()))
                .vat(i.getVat())
                .build();
    }

    public InvoiceItem toDomain(InvoiceItemDbDto dto) {
        return InvoiceItem.builder()
                .idInvoice(dto.getIdInvoice())
                .idInvoiceItem(dto.getIdInvoiceItem())
                .name(dto.getName())
                .pkwiu(dto.getPkwiu())
                .unit(dto.getUnit())
                .quantity(dto.getQuantity())
                .amount(Money.of(dto.getAmount(),"PLN"))
                .vat(dto.getVat())
                .build();
    }
}