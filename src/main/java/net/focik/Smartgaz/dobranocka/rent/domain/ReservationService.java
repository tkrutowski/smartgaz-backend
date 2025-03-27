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
        log.info("Finding available beds from {} to {}", start, end);

        List<Reservation> reservationList = reservationRepository.findAll();
        log.debug("Total reservations found: {}", reservationList.size());

        List<Bed> allBeds = new ArrayList<>(roomService.findAllBeds());
        log.debug("Total beds available in system: {}", allBeds.size());

        // Lista zajętych łóżek
        List<Bed> occupiedBeds = new ArrayList<>();

        for (Reservation reservation : reservationList) {
            boolean overlaps = !((reservation.getEndDate().isBefore(start) || reservation.getEndDate().isEqual(start))
                    || (reservation.getStartDate().isAfter(end) || reservation.getStartDate().isEqual(end)));

            log.debug("Reservation {} ({} - {}) overlaps: {}", reservation.getId(), reservation.getStartDate(), reservation.getEndDate(), overlaps);

            if (overlaps) {
                for (ReservationBed reservationBed : reservation.getBeds()) {
                    occupiedBeds.add(reservationBed.getBed());
                }
            }
        }

        log.info("Occupied beds count: {}", occupiedBeds.size());

        // Usunięcie zajętych łóżek z listy wszystkich łóżek
        allBeds.removeAll(occupiedBeds);

        log.info("Available beds count: {}", allBeds.size());
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

    /**
     * check bed availability to extend reservation
     * @param start
     * @param end
     * @param bedId
     * @param reservationId
     * @return
     */
    public boolean checkBedAvailability(LocalDate start, LocalDate end, int bedId, int reservationId) {
        log.info("Checking availability for bedId: {}, reservationId: {}, start: {}, end: {}", bedId, reservationId, start, end);
        List<Reservation> conflictingReservations = new ArrayList<>();
        for (Reservation reservation : reservationRepository.findAll()) {
            boolean overlaps = !((reservation.getEndDate().isBefore(start) || reservation.getEndDate().isEqual(start))
                    || (reservation.getStartDate().isAfter(end) || reservation.getStartDate().isEqual(end)));
            if (overlaps) {
                conflictingReservations.add(reservation);
            }
        }
        boolean available = true;
        for (Reservation reservation : conflictingReservations) {
            long count = reservation.getBeds().stream()
                    .map(ReservationBed::getBed)
                    .filter(bed -> bed.getId() == bedId)
                    .count();
            if (count > 0 && reservation.getId() != reservationId) {
                log.debug("Bed {} is reserved in reservation {}", bedId, reservation.getId());
                available = false;
                break;
            }
        }

        log.info("BedId {} availability: {}", bedId, available);

        return available;
    }

    /**
     * Check if any reservation ends that day for that bed
     * @param bedId bed to check
     * @param date date to check
     * @return tue if YES
     */
    public boolean isEndDateByBed(Integer bedId, LocalDate date) {
        List<Reservation> reservationList = reservationRepository.findAll().stream()
                .filter(reservation -> reservation.getEndDate().isEqual(date))
                .toList();

        boolean available = false;

        for (Reservation reservation : reservationList) {
            long count = reservation.getBeds().stream()
                    .map(ReservationBed::getBed)
                    .filter(bed -> bed.getId() == bedId)
                    .count();
            if (count > 0){
                available = true;
                break;
            }
        }

        return available;
    }

    /**
     * Check if any reservation starts that day for that bed
     * @param bedId bed to check
     * @param date date to check
     * @return tue if YES
     */
    public boolean isStartDateByBed(Integer bedId, LocalDate date) {
        List<Reservation> reservationList = reservationRepository.findAll().stream()
                .filter(reservation -> reservation.getStartDate().isEqual(date))
                .toList();

        boolean available = false;

        for (Reservation reservation : reservationList) {
            long count = reservation.getBeds().stream()
                    .map(ReservationBed::getBed)
                    .filter(bed -> bed.getId() == bedId)
                    .count();
            if (count > 0){
                available = true;
                break;
            }
        }

        return available;
    }
}
