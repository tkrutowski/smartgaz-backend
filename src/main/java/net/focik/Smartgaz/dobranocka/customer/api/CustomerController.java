package net.focik.Smartgaz.dobranocka.customer.api;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.focik.Smartgaz.dobranocka.customer.api.dto.CustomerDto;
import net.focik.Smartgaz.dobranocka.customer.domain.Customer;
import net.focik.Smartgaz.dobranocka.customer.domain.port.primary.AddCustomerUseCase;
import net.focik.Smartgaz.dobranocka.customer.domain.port.primary.DeleteCustomerUseCase;
import net.focik.Smartgaz.dobranocka.customer.domain.port.primary.GetCustomerUseCase;
import net.focik.Smartgaz.dobranocka.customer.domain.port.primary.UpdateCustomerUseCase;
import net.focik.Smartgaz.utils.exceptions.ExceptionHandling;
import net.focik.Smartgaz.utils.exceptions.HttpResponse;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/dobranocka/customer")
//@CrossOrigin
public class CustomerController extends ExceptionHandling {

    private final ModelMapper mapper;
    private final AddCustomerUseCase addCustomerUseCase;
    private final UpdateCustomerUseCase updateCustomerUseCase;
    private final GetCustomerUseCase getCustomerUseCase;
    private final DeleteCustomerUseCase deleteCustomerUseCase;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('DOBRANOCKA_CUSTOMER_READ_ALL','DOBRANOCKA_CUSTOMER_READ') or hasRole('ROLE_ADMIN')")
    ResponseEntity<CustomerDto> getById(@PathVariable int id) {
        log.info("Request to get customer with id: {}.",id);
        Customer customer = getCustomerUseCase.findById(id);
        if (customer == null) {
            log.warn("No customer found.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        log.info("Customer found: {}", customer);
        CustomerDto dto = mapper.map(customer, CustomerDto.class);
        log.debug("Mapped to Customer dto: {}", dto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('DOBRANOCKA_CUSTOMER_READ_ALL','DOBRANOCKA_CUSTOMER_READ') or hasRole('ROLE_ADMIN')")
    ResponseEntity<List<CustomerDto>> getAllCustomers() {
        log.info("Request to get customers.");

        List<Customer> customerList = getCustomerUseCase.findAll();

        if (customerList.isEmpty()) {
            log.warn("No customers found.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        log.info("Found {} customers.", customerList.size());
        return new ResponseEntity<>(customerList.stream()
                .peek(customer -> log.debug("Found customer {}", customer))
                .map(customer -> mapper.map(customer, CustomerDto.class))
                .peek(customerDto -> log.debug("Mapped found customer {}", customerDto))
                .toList(), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('DOBRANOCKA_CUSTOMER_WRITE_ALL','DOBRANOCKA_CUSTOMER_WRITE') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<CustomerDto> addCustomer(@RequestBody CustomerDto customerDto) {
        log.info("Request to add a new customer received with data: {}", customerDto);

        Customer customerToAdd = mapper.map(customerDto, Customer.class);
        log.debug("Mapped Customer DTO to domain object: {}", customerToAdd);

        Customer addedCustomer = addCustomerUseCase.addCustomer(customerToAdd);
        log.info("Customer added successfully: {}", addedCustomer);

        log.info(addedCustomer.getId() > 0 ? "Customer added with id = " + addedCustomer.getId() : "No customer added!");

        CustomerDto dto = mapper.map(addedCustomer, CustomerDto.class);
        log.debug("Mapped Customer DTO to domain object: {}", dto);

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('DOBRANOCKA_CUSTOMER_WRITE_ALL','DOBRANOCKA_CUSTOMER_WRITE') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<CustomerDto> updateCustomer(@RequestBody CustomerDto customerDto) {
        log.info("Request to edit a customer received with data: {}", customerDto);

        Customer customerToUpdate = mapper.map(customerDto, Customer.class);
        log.debug("Mapped Customer DTO to domain object: {}", customerToUpdate);

        Customer updatedCustomer = updateCustomerUseCase.updateCustomer(customerToUpdate);
        log.info("Customer updated successfully: {}", updatedCustomer);

        CustomerDto dto = mapper.map(updatedCustomer, CustomerDto.class);
        log.debug("Mapped Computer DTO to domain object: {}", dto);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping("/{idCustomer}")
    @PreAuthorize("hasAnyAuthority('DOBRANOCKA_CUSTOMER_DELETE_ALL','DOBRANOCKA_CUSTOMER_DELETE') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<HttpResponse> deleteCustomer(@PathVariable int idCustomer) {
        log.info("Request to delete Customer with id: {}", idCustomer);

        deleteCustomerUseCase.deleteCustomer(idCustomer);

        log.info("Customer with id: {} deleted successfully", idCustomer);

        return response(HttpStatus.NO_CONTENT, "Klient usunięty.");
    }

    private ResponseEntity<HttpResponse> response(HttpStatus status, String message) {
        HttpResponse body = new HttpResponse(status.value(), status, status.getReasonPhrase(), message);
        return new ResponseEntity<>(body, status);
    }
}
