package net.focik.Smartgaz.dobranocka.rent.domain;

import net.focik.Smartgaz.dobranocka.rent.domain.port.secondary.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationService reservationService;


    @Test
    void shouldReturnTrue_whenBedIsAvailable() {
        Reservation reservation5 = new Reservation();
        reservation5.setId(100);
        reservation5.setStartDate(LocalDate.of(2025, 3, 6));
        reservation5.setEndDate(LocalDate.of(2025, 4, 6));

        Reservation reservation19 = new Reservation();
        reservation19.setId(200);
        reservation19.setStartDate(LocalDate.of(2025, 4, 16));
        reservation19.setEndDate(LocalDate.of(2025, 4, 18));

        Reservation reservation1 = new Reservation();
        reservation1.setId(300);
        reservation1.setStartDate(LocalDate.of(2025, 1, 8));
        reservation1.setEndDate(LocalDate.of(2025, 2, 8));

        Bed bed = new Bed();
        int bedId = 1;
        bed.setId(bedId);

        ReservationBed reservationBed = new ReservationBed();
        reservationBed.setBed(bed);
        reservation5.setBeds(List.of(reservationBed));
        reservation19.setBeds(List.of(reservationBed));
        reservation1.setBeds(List.of(reservationBed));

        when(reservationRepository.findAll()).thenReturn(List.of(reservation5, reservation19, reservation1));
        LocalDate start = LocalDate.of(2025, 3, 6);
        LocalDate end = LocalDate.of(2025, 4, 15);

        int reservationId = 100;
        boolean result = reservationService.checkBedAvailability(start, end, bedId, reservationId);

        assertTrue(result);
    }

    @Test
    void shouldReturnFalse_whenBedIsAlreadyReserved() {
        Reservation reservation5 = new Reservation();
        reservation5.setId(100);
        reservation5.setStartDate(LocalDate.of(2025, 3, 6));
        reservation5.setEndDate(LocalDate.of(2025, 4, 6));

        Reservation reservation19 = new Reservation();
        reservation19.setId(200);
        reservation19.setStartDate(LocalDate.of(2025, 4, 16));
        reservation19.setEndDate(LocalDate.of(2025, 4, 18));

        Reservation reservation1 = new Reservation();
        reservation1.setId(300);
        reservation1.setStartDate(LocalDate.of(2025, 1, 8));
        reservation1.setEndDate(LocalDate.of(2025, 2, 8));

        Bed bed = new Bed();
        int bedId = 1;
        bed.setId(bedId);

        ReservationBed reservationBed = new ReservationBed();
        reservationBed.setBed(bed);
        reservation5.setBeds(List.of(reservationBed));
        reservation19.setBeds(List.of(reservationBed));
        reservation1.setBeds(List.of(reservationBed));

        when(reservationRepository.findAll()).thenReturn(List.of(reservation5, reservation19, reservation1));
        LocalDate start = LocalDate.of(2025, 3, 6);
        LocalDate end = LocalDate.of(2025, 4, 17);

        int reservationId = 100;
        boolean result = reservationService.checkBedAvailability(start, end, bedId, reservationId);

        assertFalse(result);
    }

    @Test
    void shouldReturnTrue_whenBedIsBecomeAvailable() {
        Reservation reservation5 = new Reservation();
        reservation5.setId(100);
        reservation5.setStartDate(LocalDate.of(2025, 3, 6));
        reservation5.setEndDate(LocalDate.of(2025, 4, 6));

        Reservation reservation19 = new Reservation();
        reservation19.setId(200);
        reservation19.setStartDate(LocalDate.of(2025, 4, 16));
        reservation19.setEndDate(LocalDate.of(2025, 4, 18));

        Reservation reservation1 = new Reservation();
        reservation1.setId(300);
        reservation1.setStartDate(LocalDate.of(2025, 1, 8));
        reservation1.setEndDate(LocalDate.of(2025, 2, 8));


        Bed bed = new Bed();
        int bedId = 1;
        bed.setId(bedId);

        ReservationBed reservationBed = new ReservationBed();
        reservationBed.setBed(bed);
        reservation5.setBeds(List.of(reservationBed));
        reservation19.setBeds(List.of(reservationBed));
        reservation1.setBeds(List.of(reservationBed));

        when(reservationRepository.findAll()).thenReturn(List.of(reservation5, reservation19, reservation1));
        LocalDate start = LocalDate.of(2025, 3, 6);
        LocalDate end = LocalDate.of(2025, 4, 16);

        int reservationId = 100;
        boolean result = reservationService.checkBedAvailability(start, end, bedId, reservationId);

        assertTrue(result);
    }
}