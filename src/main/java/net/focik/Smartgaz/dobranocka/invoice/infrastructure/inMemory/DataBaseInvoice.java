package net.focik.Smartgaz.dobranocka.invoice.infrastructure.inMemory;


import net.focik.Smartgaz.dobranocka.invoice.infrastructure.dto.InvoiceDbDto;

import java.util.HashMap;
import java.util.Map;


public class DataBaseInvoice {
    private static HashMap<Integer, InvoiceDbDto> invoiceDbDtoHashMap;

    public static Map<Integer, InvoiceDbDto> getInvoiceDbDtoHashMap() {
        if (invoiceDbDtoHashMap == null)
            invoiceDbDtoHashMap = new HashMap<>();
        return invoiceDbDtoHashMap;
    }


    private DataBaseInvoice() {
    }
}
