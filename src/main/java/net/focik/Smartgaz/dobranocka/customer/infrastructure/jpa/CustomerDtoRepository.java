package net.focik.Smartgaz.dobranocka.customer.infrastructure.jpa;

import net.focik.Smartgaz.dobranocka.customer.infrastructure.dto.CustomerDbDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface CustomerDtoRepository extends JpaRepository<CustomerDbDto, Integer> {

    List<CustomerDbDto> findAllByNameAndFirstName(String name, String firstName);
    List<CustomerDbDto> findAllByNip(String nip);
}
