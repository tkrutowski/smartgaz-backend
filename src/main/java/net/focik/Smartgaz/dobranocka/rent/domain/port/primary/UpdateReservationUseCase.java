package net.focik.Smartgaz.dobranocka.rent.domain.port.primary;


import net.focik.Smartgaz.dobranocka.rent.domain.Reservation;

public interface UpdateReservationUseCase {
    Reservation updateReservation(Reservation reservation);
}
