package net.focik.Smartgaz.dobranocka.rent.domain.port.primary;


import net.focik.Smartgaz.dobranocka.rent.domain.Reservation;

public interface AddReservationUseCase {
    Reservation addReservation(Reservation reservation);
}
