package net.focik.Smartgaz.dobranocka.rent.infrastructure.jpa;

import net.focik.Smartgaz.dobranocka.rent.infrastructure.dto.ReservationDbDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

interface ReservationDtoRepository extends JpaRepository<ReservationDbDto, Integer> {

    List<ReservationDbDto> findAllByCustomer_Id(Integer customerId);

    List<ReservationDbDto> findAllByStartDateIsBeforeAndEndDateIsAfter(LocalDate date, LocalDate sameDate);

    List<ReservationDbDto> findAllByEndDate(LocalDate endDate);

    List<ReservationDbDto> findAllByStartDate(LocalDate startDate);
}
