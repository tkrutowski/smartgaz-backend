package net.focik.Smartgaz.dobranocka.customer.domain.customer.port.primary;


import net.focik.Smartgaz.dobranocka.customer.domain.customer.Customer;

public interface UpdateCustomerUseCase {
    Customer updateCustomer(Customer customer);
}
