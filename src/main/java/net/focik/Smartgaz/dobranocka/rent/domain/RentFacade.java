package net.focik.Smartgaz.dobranocka.rent.domain;

import lombok.AllArgsConstructor;
import net.focik.Smartgaz.dobranocka.customer.domain.Customer;
import net.focik.Smartgaz.dobranocka.rent.domain.port.primary.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@AllArgsConstructor
public class RentFacade implements AddRoomUseCase, GetRoomUseCase, UpdateRoomUseCase, DeleteRoomUseCase, GetReservationUseCase, AddReservationUseCase, DeleteReservationUseCase {

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
        reservationService.deleteReservation(id);
    }

    @Override
    public List<Reservation> findAllByCustomer(Customer customer) {
        return reservationService.findByCustomer(customer);
    }
}
