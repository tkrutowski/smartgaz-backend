package net.focik.Smartgaz.dobranocka.customer.domain.port.secondary;

import net.focik.Smartgaz.dobranocka.customer.domain.Customer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface CustomerRepository {

    Customer save(Customer customer);

    void delete(Integer id);

    List<Customer> findAll();

    Optional<Customer> findById(Integer id);

    Optional<Customer> findByNip(String nip);

    List<Customer> findByName(String name, String firstName);
}
