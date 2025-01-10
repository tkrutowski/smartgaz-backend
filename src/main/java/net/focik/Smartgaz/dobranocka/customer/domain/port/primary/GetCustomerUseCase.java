package net.focik.Smartgaz.dobranocka.customer.domain.port.primary;


import net.focik.Smartgaz.dobranocka.customer.domain.Customer;

import java.util.List;

public interface GetCustomerUseCase {
    Customer findById(Integer id);
    List<Customer> findAll();

}
