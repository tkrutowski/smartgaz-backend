package net.focik.Smartgaz.dobranocka.settings.api;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.focik.Smartgaz.dobranocka.settings.domain.company.Company;
import net.focik.Smartgaz.dobranocka.settings.domain.company.port.primary.GetCompanyUseCase;
import net.focik.Smartgaz.dobranocka.settings.domain.company.port.primary.UpdateCompanyUseCase;
import net.focik.Smartgaz.utils.exceptions.ExceptionHandling;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/dobranocka")
//@CrossOrigin
public class SettingsController extends ExceptionHandling {

    private final UpdateCompanyUseCase updateCompanyUseCase;
    private final GetCompanyUseCase getCompanyUseCase;

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('DOBRANOCKA_COMPANY_READ_ALL','DOBRANOCKA_COMPANY_READ') or hasRole('ROLE_ADMIN')")
    ResponseEntity<Company> getCompanyDetails() {
        log.info("Request to get company details");
        Company company = getCompanyUseCase.get();
        if (company == null) {
            log.warn("No company found.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        log.info("Company found: {}", company);
        return new ResponseEntity<>(company, HttpStatus.OK);
    }


    @PutMapping
    @PreAuthorize("hasAnyAuthority('DOBRANOCKA_COMPANY_WRITE_ALL','DOBRANOCKA_COMPANY_WRITE') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Company> updateCompany(@RequestBody Company company) {
        log.info("Request to edit a customer company with data: {}", company);

        Company updatedCompany = updateCompanyUseCase.updateCompany(company);
        log.info("Company updated successfully: {}", updatedCompany);

        return new ResponseEntity<>(updatedCompany, HttpStatus.OK);
    }

}
