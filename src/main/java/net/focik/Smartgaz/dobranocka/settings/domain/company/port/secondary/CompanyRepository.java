package net.focik.Smartgaz.dobranocka.settings.domain.company.port.secondary;

import net.focik.Smartgaz.dobranocka.settings.domain.company.Company;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface CompanyRepository {

    Company save(Company customer);

    Optional<Company> get();

    Optional<Company> findById(String companyName);
}
