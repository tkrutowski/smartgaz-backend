package net.focik.Smartgaz.dobranocka.customer.domain.customer;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.focik.Smartgaz.dobranocka.customer.domain.customer.port.secondary.CustomerRepository;
import net.focik.Smartgaz.dobranocka.customer.domain.exception.CustomerAlreadyExistException;
import net.focik.Smartgaz.dobranocka.customer.domain.exception.CustomerNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
class CustomerService implements ICustomerService {

    private final CustomerRepository customerRepository;

    public Customer addCustomer(Customer customer) {
        validate(customer);
        int id = customerRepository.save(customer).getId();
        return findById(id);
    }

    public Customer updateCustomer(Customer customer) {
        findById(customer.getId());
        return customerRepository.save(customer);
    }

    private void validate(Customer customer) {
        //TODO jezeli jest nip to sprawdzić po nipie, a jeżęli nie to imie i nazwisko

        if (StringUtils.isNotEmpty(customer.getNip())) {
            Optional<Customer> byNip = customerRepository.findByNip(customer.getNip());
            if (byNip.isPresent()) {
                throw new CustomerAlreadyExistException("Customer with NIP: " + customer.getNip() + " already exists");
            }
        }

//        if (customer.getCustomerType() == CustomerType.COMPANY) {
//            Optional<Customer> byNip = customerRepository.findByNip(customer.getNip());
//            if (byNip.isPresent()) {
//                throw new CustomerAlreadyExistException("Klient o NIP-ie " + customer.getNip() + "już istnieje.");
//            }
//        }
    }

    public void deleteCustomer(Integer id) {
        customerRepository.delete(id);
    }

    public Customer findById(Integer id) {
        Optional<Customer> byId = customerRepository.findById(id);
        if (byId.isEmpty()) {
            throw new CustomerNotFoundException("id", String.valueOf(id));
        }
        return byId.get();
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

}
