package net.focik.Smartgaz.dobranocka.customer.domain;

import lombok.AllArgsConstructor;
import net.focik.Smartgaz.dobranocka.customer.domain.port.primary.AddCustomerUseCase;
import net.focik.Smartgaz.dobranocka.customer.domain.port.primary.DeleteCustomerUseCase;
import net.focik.Smartgaz.dobranocka.customer.domain.port.primary.GetCustomerUseCase;
import net.focik.Smartgaz.dobranocka.customer.domain.port.primary.UpdateCustomerUseCase;
import net.focik.Smartgaz.dobranocka.invoice.domain.invoice.Invoice;
import net.focik.Smartgaz.dobranocka.invoice.domain.invoice.InvoiceFacade;
import net.focik.Smartgaz.dobranocka.rent.domain.RentFacade;
import net.focik.Smartgaz.dobranocka.rent.domain.Reservation;
import net.focik.Smartgaz.utils.exceptions.ObjectCanNotBeDeletedException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class CustomerFacade implements AddCustomerUseCase, UpdateCustomerUseCase, GetCustomerUseCase, DeleteCustomerUseCase {

    private final ICustomerService customerService;
    private final InvoiceFacade invoiceFacade;
    private final RentFacade rentFacade;

    @Override
    public Customer addCustomer(Customer customer) {
        return customerService.addCustomer(customer);
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        return customerService.updateCustomer(customer);
    }

    @Override
    public void deleteCustomer(Integer id) {
        Customer byId = findById(id);

        List<Invoice> invoicesByCustomer = invoiceFacade.findAllByCustomer(byId);
        if (!invoicesByCustomer.isEmpty()) {
            throw new ObjectCanNotBeDeletedException("Istnieją faktury.");
        }
        List<Reservation> reservationsByCustomer = rentFacade.findAllByCustomer(byId);
        if (!reservationsByCustomer.isEmpty()) {
            throw new ObjectCanNotBeDeletedException("Istnieją rezerwacje.");
        }
        customerService.deleteCustomer(id);
    }

    @Override
    public Customer findById(Integer id) {
        return customerService.findById(id);
    }

    @Override
    public List<Customer> findAll() {
        return customerService.findAll();
    }
}
