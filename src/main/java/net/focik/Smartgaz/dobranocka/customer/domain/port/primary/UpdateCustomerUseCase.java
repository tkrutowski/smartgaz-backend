package net.focik.Smartgaz.dobranocka.customer.domain.port.primary;


import net.focik.Smartgaz.dobranocka.customer.domain.Customer;

public interface UpdateCustomerUseCase {
    Customer updateCustomer(Customer customer);
}
