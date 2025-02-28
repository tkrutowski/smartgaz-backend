package net.focik.Smartgaz.dobranocka.rent.infrastructure.jpa;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import net.focik.Smartgaz.dobranocka.customer.domain.Customer;
import net.focik.Smartgaz.dobranocka.rent.domain.Reservation;
import net.focik.Smartgaz.dobranocka.rent.domain.port.secondary.ReservationRepository;
import net.focik.Smartgaz.dobranocka.rent.infrastructure.dto.ReservationDbDto;
import net.focik.Smartgaz.dobranocka.rent.infrastructure.mapper.JpaReservationMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ReservationRepositoryAdapter implements ReservationRepository {

    private final ReservationDtoRepository reservationDtoRepository;
    private final ModelMapper mapper;
    private final JpaReservationMapper reservationMapper;

    @Override
    public Reservation save(Reservation reservation) {
        ReservationDbDto reservationDbDto = mapper.map(reservation, ReservationDbDto.class);
        if (reservationDbDto.getId().equals(0)) {
            reservationDbDto.setId(null);
        }

        if (reservationDbDto.getBeds() != null) {
            reservationDbDto.getBeds().forEach(bed -> {
                if (bed.getId().equals(0)) {
                    bed.setId(null);
                }
                bed.setReservation(reservationDbDto);
            });
        }

        if (reservationDbDto.getInvoice() != null && reservationDbDto.getInvoice().getIdInvoice() == 0) {
            reservationDbDto.setInvoice(null);
        }

        ReservationDbDto saved = reservationDtoRepository.save(reservationDbDto);
        return mapper.map(saved, Reservation.class);
    }

    @Override
    public List<Reservation> findAllByCustomer(Customer customer) {
        return reservationDtoRepository.findAllByCustomer_Id(customer.getId()).stream()
                .map(reservationMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        reservationDtoRepository.deleteById(id);
    }

    @Override
    public Optional<Reservation> findById(Integer id) {
        return reservationDtoRepository.findById(id)
                .map(reservationMapper::toDomain);
    }

    @Override
    public List<Reservation> findAll() {
        return reservationDtoRepository.findAll().stream()
                .map(reservationMapper::toDomain)
                .toList();
    }

    @Override
    public List<Reservation> findActiveReservationsByDate(LocalDate date) {
        return reservationDtoRepository.findAllByStartDateIsBeforeAndEndDateIsAfter(date, date).stream()
                .map(reservationMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional //needed for CRON
    public List<Reservation> findActiveReservationsByEndDate(LocalDate endDate) {
        return reservationDtoRepository.findAllByEndDate(endDate).stream()
                .map(reservationMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional //needed for CRON
    public List<Reservation> findActiveReservationsByStartDate(LocalDate endDate) {
        return reservationDtoRepository.findAllByStartDate(endDate).stream()
                .map(reservationMapper::toDomain)
                .collect(Collectors.toList());
    }
}
