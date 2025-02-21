package net.focik.Smartgaz.dobranocka.rent.domain;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.focik.Smartgaz.dobranocka.customer.domain.Customer;
import net.focik.Smartgaz.dobranocka.rent.domain.port.primary.*;
import net.focik.Smartgaz.utils.exceptions.ObjectCanNotBeDeletedException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class RentFacade implements AddRoomUseCase, GetRoomUseCase, UpdateRoomUseCase, DeleteRoomUseCase, GetReservationUseCase, AddReservationUseCase, DeleteReservationUseCase, UpdateReservationUseCase {

    private final RoomService roomService;
    private final ReservationService reservationService;

    @Override
    public Room addRoom(Room room) {
        return roomService.addRoom(room);
    }

    @Override
    public Room findById(Integer id) {
        return roomService.findById(id);
    }

    @Override
    public List<Room> findAll() {
        return roomService.findAll();
    }

    @Override
    public List<Room> findRoomsWithAvailableBeds(LocalDate start, LocalDate end) {
        List<Bed> availableBeds = reservationService.findAvailableBeds(start, end);
        List<Room> rooms = roomService.findAll();
        for (Room room : rooms) {
            room.getBeds().removeIf(bed -> !availableBeds.contains(bed));
        }

        return rooms.stream().filter(room -> !room.getBeds().isEmpty()).toList();
    }

    @Override
    public Room updateRoom(Room room) {
        return roomService.updateRoom(room);
    }

    @Override
    public Bed updateBed(Bed bed) {
        return roomService.updateBed(bed);
    }

    @Override
    public void deleteRoom(Integer id) {
        roomService.deleteRoom(id);
    }

    @Override
    public Reservation addReservation(Reservation reservation) {
        return reservationService.addReservation(reservation);
    }

    @Override
    public Reservation findByReservationId(Integer id) {
        return reservationService.findById(id);
    }

    @Override
    public List<Reservation> findAll(ReservationStatus status) {
        return reservationService.findByStatus(status);
    }

    @Override
    public void deleteReservation(Integer id) {
        Reservation reservation = findByReservationId(id);
        if (reservation.getInvoiceId() > 0){
            throw new ObjectCanNotBeDeletedException("Istnieją faktury.");
        }
        reservationService.deleteReservation(id);
    }

    @Override
    public List<Reservation> findAllByCustomer(Customer customer) {
        return reservationService.findByCustomer(customer);
    }

    @Override
    public boolean checkBedAvailability(LocalDate start, LocalDate end, int bedId, int reservationId) {
        return reservationService.checkBedAvailability(start, end, bedId, reservationId);
    }

    @Override
    public Reservation updateReservation(Reservation reservation) {
        return reservationService.updateReservation(reservation);
    }

    @Override
    public void updateInvoiceInReservation(int invoiceId, List<Integer> reservationIds) {
        log.info("Start updating invoice in reservations. InvoiceId: {}, ReservationIds: {}", invoiceId, reservationIds);

        for (Integer reservationId : reservationIds) {
            log.info("Processing reservationId: {}", reservationId);
            Reservation reservation = reservationService.findById(reservationId);
            if (reservation == null) {
                log.warn("Reservation with id {} not found. Skipping...", reservationId);
                continue;
            }
            log.info("Updating reservationId: {} with invoiceId: {}", reservationId, invoiceId);
            reservation.setInvoiceId(invoiceId);

            reservationService.updateReservation(reservation);
            log.info("Successfully updated reservationId: {}", reservationId);
        }
        log.info("Finished updating invoice in reservations.");
    }

    @Scheduled(cron = "${scheduler.rent.after}")
    public void setStatusAfterRent() {
        LocalDate now = LocalDate.now();
        log.info("Schedule SetStatusAfterRent started on: {}", now);

        List<Reservation> activeReservations = reservationService.findAllActiveReservationsByEndDate(now);
        log.info("Active reservations: {}", activeReservations);

        for (Reservation reservation : activeReservations) {
            log.info("Trying to set status TO_CLEAN on {} for reservation; {}", now, reservation);
            reservation.getBeds().stream().map(ReservationBed::getBed)
                    .peek(bed -> log.info("Trying to set status TO_CLEAN for: {}", bed))
                    .forEach(bed -> {
                        Room byBed = roomService.findByBed(bed.getId());
                        if (byBed != null) {
                            byBed.getBeds().forEach(bed1 -> {
                                if (bed.getId() == bed1.getId()) {
                                    bed1.setBedStatus(BedStatus.TO_CLEAN);
                                    roomService.updateRoom(byBed);
                                }
                            });
                        }
                    });
            log.info("Set status TO_CLEAN on {} for reservation; {}", now, reservation);
        }
        log.info("Schedule SetStatusAfterRent ended on: {}", now);
    }

    @Scheduled(cron = "${scheduler.rent.start}")
    public void setStatusWhenRent() {
        LocalDate now = LocalDate.now();
        log.info("Schedule SetStatusWhenRent started on: {}", now);

        List<Reservation> activeReservations = reservationService.findAllActiveReservationsByStartDate(now);
        log.info("Active reservations: {}", activeReservations);

        for (Reservation reservation : activeReservations) {
            log.info("Trying to set status OCCUPIED on {} for reservation; {}", now, reservation);
            reservation.getBeds().stream().map(ReservationBed::getBed)
                    .peek(bed -> log.info("Trying to set status OCCUPIED for: {}", bed))
                    .forEach(bed -> {
                        Room byBed = roomService.findByBed(bed.getId());
                        if (byBed != null) {
                            byBed.getBeds().forEach(bed1 -> {
                                if (bed.getId() == bed1.getId()) {
                                    bed1.setBedStatus(BedStatus.OCCUPIED);
                                    roomService.updateRoom(byBed);
                                }
                            });
                        }
                    });
            log.info("Set status OCCUPIED on {} for reservation; {}", now, reservation);
        }
        log.info("Schedule SetStatusWhenRent ended on: {}", now);
    }
}
