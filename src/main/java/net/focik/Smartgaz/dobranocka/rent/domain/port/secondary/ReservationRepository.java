package net.focik.Smartgaz.dobranocka.rent.domain.port.secondary;

import net.focik.Smartgaz.dobranocka.customer.domain.Customer;
import net.focik.Smartgaz.dobranocka.rent.domain.Reservation;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public interface ReservationRepository {

    Reservation save(Reservation room);

    void delete(Integer id);

    Optional<Reservation> findById(Integer id);

    List<Reservation> findAllByCustomer(Customer customer);

    List<Reservation> findAll();

    List<Reservation> findActiveReservationsByDate(LocalDate date);

    List<Reservation> findActiveReservationsByEndDate(LocalDate endDate);

    List<Reservation> findActiveReservationsByStartDate(LocalDate startDate);
}
