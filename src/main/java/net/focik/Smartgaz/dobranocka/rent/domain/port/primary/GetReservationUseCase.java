package net.focik.Smartgaz.dobranocka.rent.domain.port.primary;


import net.focik.Smartgaz.dobranocka.customer.domain.Customer;
import net.focik.Smartgaz.dobranocka.rent.domain.Reservation;
import net.focik.Smartgaz.dobranocka.rent.domain.ReservationStatus;
import net.focik.Smartgaz.dobranocka.rent.domain.Room;

import java.time.LocalDate;
import java.util.List;

public interface GetReservationUseCase {
    Reservation findByReservationId(Integer id);

    List<Reservation> findAll(ReservationStatus status);

    List<Room> findRoomsWithAvailableBeds(LocalDate start, LocalDate end);

    List<Reservation> findAllByCustomer(Customer customer);

    boolean checkBedAvailability(LocalDate start, LocalDate end, int bedId, int reservationId);

    boolean isEndDateByBed(Integer bed, LocalDate end);

    boolean isStartDateByBed(Integer bed, LocalDate date);
}
