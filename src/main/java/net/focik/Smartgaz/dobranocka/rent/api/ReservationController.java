package net.focik.Smartgaz.dobranocka.rent.api;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.focik.Smartgaz.dobranocka.rent.api.dto.ReservationDto;
import net.focik.Smartgaz.dobranocka.rent.api.dto.RoomDto;
import net.focik.Smartgaz.dobranocka.rent.api.mapper.ApiReservationMapper;
import net.focik.Smartgaz.dobranocka.rent.api.mapper.ApiRoomMapper;
import net.focik.Smartgaz.dobranocka.rent.domain.Reservation;
import net.focik.Smartgaz.dobranocka.rent.domain.ReservationStatus;
import net.focik.Smartgaz.dobranocka.rent.domain.Room;
import net.focik.Smartgaz.dobranocka.rent.domain.port.primary.*;
import net.focik.Smartgaz.utils.exceptions.ExceptionHandling;
import net.focik.Smartgaz.utils.exceptions.HttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Log4j2
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/dobranocka/reservation")
//@CrossOrigin
public class ReservationController extends ExceptionHandling {

    private final ApiReservationMapper mapper;
    private final ApiRoomMapper roomMapper;
    private final AddReservationUseCase addReservationUseCase;
    private final GetReservationUseCase getReservationUseCase;
    private final UpdateReservationUseCase updateReservationUseCase;
    private final DeleteReservationUseCase deleteReservationUseCase;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('DOBRANOCKA_RESERVATION_READ_ALL','DOBRANOCKA_RESERVATION_READ') or hasRole('ROLE_ADMIN')")
    ResponseEntity<ReservationDto> getById(@PathVariable int id) {
        log.info("Request to get reservation with id: {}.",id);
        Reservation reservation = getReservationUseCase.findByReservationId(id);
        if (reservation == null) {
            log.warn("No reservation found.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        log.info("Reservation found: {}", reservation);
        ReservationDto dto = mapper.toDto(reservation);
        log.debug("Mapped to Room dto: {}", dto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('DOBRANOCKA_RESERVATION_READ_ALL','DOBRANOCKA_RESERVATION_READ') or hasRole('ROLE_ADMIN')")
    ResponseEntity<List<RoomDto>> getAvailableBeds(@RequestParam LocalDate start, @RequestParam LocalDate end) {
        log.info("Request to get available beds.");

        List<Room> availableBeds = getReservationUseCase.findRoomsWithAvailableBeds(start, end);

        if (availableBeds.isEmpty()) {
            log.warn("No beds found.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        log.info("Found {} beds.", availableBeds.size());
        return new ResponseEntity<>(availableBeds.stream()
                .peek(bed -> log.debug("Found bed {}", bed))
                .map(roomMapper::toDto)
                .peek(bedDto -> log.debug("Mapped found bed {}", bedDto))
                .toList(), HttpStatus.OK);
    }

    @GetMapping("/checkbed")
    @PreAuthorize("hasAnyAuthority('DOBRANOCKA_RESERVATION_READ_ALL','DOBRANOCKA_RESERVATION_READ') or hasRole('ROLE_ADMIN')")
    ResponseEntity<Boolean> checkBedAvailability(@RequestParam LocalDate start, @RequestParam LocalDate end, @RequestParam int bedId, @RequestParam int reservationId) {
        log.info("Request to check bed availability for bedId {} and reservationId {}.", bedId, reservationId);

        boolean result = getReservationUseCase.checkBedAvailability(start, end, bedId, reservationId);

        log.info("Result of check bed availability for bedId {} and reservationId {} = {}.", bedId, reservationId, result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('DOBRANOCKA_RESERVATION_READ_ALL','DOBRANOCKA_RESERVATION_READ') or hasRole('ROLE_ADMIN')")
    ResponseEntity<List<ReservationDto>> getReservationByStatus(@RequestParam(required = false, defaultValue = "ALL") ReservationStatus status) {
        log.info("Request to get reservation by status: {}.", status);

        List<Reservation> reservationList = getReservationUseCase.findAll(status);

        if (reservationList.isEmpty()) {
            log.warn("No reservations found for status {}.", status);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        log.info("Found {} reservations.", reservationList.size());
        return new ResponseEntity<>(reservationList.stream()
                .peek(reservation -> log.debug("Found reservation {}", reservation))
                .map(mapper::toDto)
                .peek(reservationDto -> log.debug("Mapped found reservation {}", reservationDto))
                .toList(), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('DOBRANOCKA_RESERVATION_WRITE_ALL','DOBRANOCKA_RESERVATION_WRITE') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ReservationDto> addReservation(@RequestBody ReservationDto reservationDto) {
        log.info("Request to add a new Reservation received with data: {}", reservationDto);

        Reservation reservationToAdd = mapper.toDomain(reservationDto);
        log.debug("Mapped Room DTO to domain object: {}", reservationToAdd);

        Reservation addedReservation = addReservationUseCase.addReservation(reservationToAdd);
        log.debug("Reservation added successfully: {}", addedReservation);

        log.info(addedReservation.getId() > 0 ? "Reservation added with id = " + addedReservation.getId() : "No Reservation added!");

        ReservationDto dto = mapper.toDto(addedReservation);
        log.debug("Mapped Reservation DTO to domain object: {}", dto);

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('DOBRANOCKA_RESERVATION_WRITE_ALL','DOBRANOCKA_RESERVATION_WRITE') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ReservationDto> updateReservation(@RequestBody ReservationDto reservationDto) {
        log.info("Request to edit a reservation received with data: {}", reservationDto);

        Reservation reservationToUpdate = mapper.toDomain(reservationDto);
        log.debug("Mapped Reservation DTO to domain object: {}", reservationToUpdate);

        Reservation updatedReservation = updateReservationUseCase.updateReservation(reservationToUpdate);
        log.info("Reservation updated successfully: {}", updatedReservation);

        ReservationDto dto = mapper.toDto(updatedReservation);
        log.debug("Mapped Reservation DTO to domain object: {}", dto);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('DOBRANOCKA_RESERVATION_DELETE_ALL','DOBRANOCKA_RESERVATION_DELETE') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<HttpResponse> deleteReservation(@PathVariable int id) {
        log.info("Request to delete Reservation with id: {}", id);

        deleteReservationUseCase.deleteReservation(id);

        log.info("Reservation with id: {} deleted successfully", id);

        return response(HttpStatus.NO_CONTENT, "Rezerwacja usunięta.");
    }

    private ResponseEntity<HttpResponse> response(HttpStatus status, String message) {
        HttpResponse body = new HttpResponse(status.value(), status, status.getReasonPhrase(), message);
        return new ResponseEntity<>(body, status);
    }
}
