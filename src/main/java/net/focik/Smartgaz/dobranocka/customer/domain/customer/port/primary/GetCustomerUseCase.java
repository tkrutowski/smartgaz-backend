package net.focik.Smartgaz.dobranocka.customer.domain.customer.port.primary;


import net.focik.Smartgaz.dobranocka.customer.domain.customer.Customer;

import java.util.List;

public interface GetCustomerUseCase {
    Customer findById(Integer id);
    List<Customer> findAll();

}
