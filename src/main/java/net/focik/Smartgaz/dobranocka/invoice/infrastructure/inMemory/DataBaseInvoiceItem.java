package net.focik.Smartgaz.dobranocka.invoice.infrastructure.inMemory;


import net.focik.Smartgaz.dobranocka.invoice.infrastructure.dto.InvoiceItemDbDto;

import java.util.HashMap;
import java.util.Map;


public class DataBaseInvoiceItem {
    private static HashMap<Long, InvoiceItemDbDto> invoiceItemDbDtoHashMap;

    public static Map<Long, InvoiceItemDbDto> getInvoiceItemDbDtoHashMap() {
        if (invoiceItemDbDtoHashMap == null)
            invoiceItemDbDtoHashMap = new HashMap<>();
        return invoiceItemDbDtoHashMap;
    }


    private DataBaseInvoiceItem() {
    }
}
