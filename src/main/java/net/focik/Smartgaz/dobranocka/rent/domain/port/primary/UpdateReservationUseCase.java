package net.focik.Smartgaz.dobranocka.rent.domain.port.primary;


import net.focik.Smartgaz.dobranocka.rent.domain.Reservation;

import java.util.List;

public interface UpdateReservationUseCase {
    Reservation updateReservation(Reservation reservation);
    void updateInvoiceInReservation(int invoiceId, List<Integer> reservationIds);
}
