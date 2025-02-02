package net.focik.Smartgaz.dobranocka.rent.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.focik.Smartgaz.dobranocka.customer.domain.Customer;
import net.focik.Smartgaz.dobranocka.rent.domain.port.secondary.ReservationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomService roomService;

    public List<Bed> findAvailableBeds(LocalDate start, LocalDate end) {
        List<Reservation> reservationList = reservationRepository.findAll();
        List<Bed> allBeds = new ArrayList<>(roomService.findAllBeds());
        List<Bed> list = reservationList.stream()
                .filter(reservation ->
                        !(reservation.getEndDate().isBefore(start) || reservation.getStartDate().isAfter(end))
                )
                .flatMap(reservation -> reservation.getBeds().stream())
                .map(ReservationBed::getBed)
                .toList();
        allBeds.removeAll(list);
        return allBeds;
    }

    public Reservation addReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public Reservation findById(Integer id) {
        return reservationRepository.findById(id).orElse(null);
    }

    public void deleteReservation(Integer id) {
        reservationRepository.delete(id);
    }

    public List<Reservation> findByCustomer(Customer customer) {
        return reservationRepository.findAllByCustomer(customer);
    }

    public List<Reservation> findByStatus(ReservationStatus status) {
        List<Reservation> all = reservationRepository.findAll();
        if (status == null || status.equals(ReservationStatus.ALL)) {
            return all;
        }
        return all.stream()
                .filter(reservation -> reservation.getReservationStatus().equals(status))
                .toList();
    }

    public Reservation updateReservation(Reservation reservation) {
        findById(reservation.getId());
        return reservationRepository.save(reservation);
    }

    public List<Reservation> findAllActiveReservations(LocalDate now) {
        return reservationRepository.findActiveReservationsByDate(now);
    }

    public List<Reservation> findAllActiveReservationsByEndDate(LocalDate now) {
        return reservationRepository.findActiveReservationsByEndDate(now);
    }

    public List<Reservation> findAllActiveReservationsByStartDate(LocalDate now) {
        return reservationRepository.findActiveReservationsByStartDate(now);
    }
}
