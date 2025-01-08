package net.focik.Smartgaz.dobranocka.customer.domain.customer;

import lombok.AllArgsConstructor;
import net.focik.Smartgaz.dobranocka.customer.domain.customer.port.primary.AddCustomerUseCase;
import net.focik.Smartgaz.dobranocka.customer.domain.customer.port.primary.DeleteCustomerUseCase;
import net.focik.Smartgaz.dobranocka.customer.domain.customer.port.primary.GetCustomerUseCase;
import net.focik.Smartgaz.dobranocka.customer.domain.customer.port.primary.UpdateCustomerUseCase;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class CustomerFacade implements AddCustomerUseCase, UpdateCustomerUseCase, GetCustomerUseCase, DeleteCustomerUseCase {

    private final ICustomerService customerService;

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
        //todo dodac sprawdzanie
//        throw new ObjectCanNotBeDeletedException("Istnieją faktury");
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
