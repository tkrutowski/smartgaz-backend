package net.focik.Smartgaz.dobranocka.settings.infrastructure.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.focik.Smartgaz.dobranocka.settings.domain.company.Company;
import net.focik.Smartgaz.dobranocka.settings.domain.company.port.secondary.CompanyRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

@Slf4j
@Repository("fileCompanyRepository")
@RequiredArgsConstructor
public class CompanyRepositoryAdapter implements CompanyRepository {

    private final ObjectMapper mapper;
    @Value("${settings.file.path}")
    private String logDirectory;

    private final String fileName = "dobranocka.json";

    @Override
    public Company save(Company company) {
        try (Writer writer = Files.newBufferedWriter(Paths.get(logDirectory, fileName))) {
            mapper.writeValue(writer, company);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Optional<Company> get() {
        try (Reader reader = Files.newBufferedReader(Paths.get(logDirectory, fileName))) {
            return Optional.of(mapper.readValue(reader, Company.class));
        } catch (IOException e) {
            log.error(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Company> findById(String companyName) {
        if (companyName == null) return get();
        return Optional.empty();
    }
}
