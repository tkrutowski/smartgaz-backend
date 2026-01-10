package net.focik.Smartgaz.dobranocka.customer.infrastructure.jpa;

import lombok.AllArgsConstructor;
import net.focik.Smartgaz.dobranocka.customer.domain.Customer;
import net.focik.Smartgaz.dobranocka.customer.domain.port.secondary.CustomerRepository;
import net.focik.Smartgaz.dobranocka.customer.infrastructure.dto.CustomerDbDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CustomerRepositoryAdapter implements CustomerRepository {

    private final CustomerDtoRepository customerDtoRepository;
    private final ModelMapper mapper;

    @Override
    public Customer save(Customer customer) {
        CustomerDbDto customerDbDto = mapper.map(customer, CustomerDbDto.class);
        if (customerDbDto.getId() == 0) {
            customerDbDto.setId(null);
        }
        CustomerDbDto saved = customerDtoRepository.save(customerDbDto);
        return mapper.map(saved, Customer.class);
    }

    @Override
    public void delete(Integer id) {
        customerDtoRepository.deleteById(id);
    }


    @Override
    public List<Customer> findAll() {
        return customerDtoRepository.findAll().stream()
                .map(dto -> mapper.map(dto, Customer.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Customer> findById(Integer id) {
        return customerDtoRepository.findById(id).map(dto -> mapper.map(dto, Customer.class));
    }

    @Override
    public Optional<Customer> findByNip(String nip) {
        Optional<CustomerDbDto> dbDto = customerDtoRepository.findAllByNip(nip).stream().findFirst();
        return dbDto.map(customerDbDto -> mapper.map(customerDbDto, Customer.class));
    }

    @Override
    public List<Customer> findByName(String name, String firstName) {
        return customerDtoRepository.findAllByNameAndFirstName(name, firstName).stream()
                .map(dto -> mapper.map(dto, Customer.class))
                .toList();
    }

}
