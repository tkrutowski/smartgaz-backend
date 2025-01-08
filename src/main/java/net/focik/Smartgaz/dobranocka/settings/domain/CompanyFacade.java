package net.focik.Smartgaz.dobranocka.settings.domain;

import lombok.AllArgsConstructor;
import net.focik.Smartgaz.dobranocka.settings.domain.company.Company;
import net.focik.Smartgaz.dobranocka.settings.domain.company.port.primary.GetCompanyUseCase;
import net.focik.Smartgaz.dobranocka.settings.domain.company.port.primary.UpdateCompanyUseCase;
import net.focik.Smartgaz.dobranocka.settings.domain.company.port.secondary.CompanyRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class CompanyFacade implements GetCompanyUseCase, UpdateCompanyUseCase {

    private final CompanyRepository companyRepository;

    @Override
    public Company get() {
        Optional<Company> company = companyRepository.get();
        return company.orElse(null);
    }

    @Override
    public Company updateCompany(Company company) {
        return companyRepository.save(company);
    }
}
