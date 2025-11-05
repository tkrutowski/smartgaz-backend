package net.focik.Smartgaz.dobranocka.settings.infrastructure.jpa;

import net.focik.Smartgaz.dobranocka.settings.infrastructure.dto.CompanyDbDto;
import org.springframework.data.jpa.repository.JpaRepository;

interface CompanyDtoRepository extends JpaRepository<CompanyDbDto, String> {
}
