package net.focik.Smartgaz.dobranocka.customer.domain.customer;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ICustomerService {

    Customer addCustomer(Customer customer);

    Customer updateCustomer(Customer customer);

    void deleteCustomer(Integer id);

    Customer findById(Integer id);

    List<Customer> findAll();
}
