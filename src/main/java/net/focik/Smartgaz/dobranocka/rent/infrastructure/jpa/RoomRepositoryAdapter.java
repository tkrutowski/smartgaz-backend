package net.focik.Smartgaz.dobranocka.rent.infrastructure.jpa;

import lombok.AllArgsConstructor;
import net.focik.Smartgaz.dobranocka.rent.domain.Room;
import net.focik.Smartgaz.dobranocka.rent.domain.port.secondary.RoomRepository;
import net.focik.Smartgaz.dobranocka.rent.infrastructure.dto.RoomDbDto;
import net.focik.Smartgaz.dobranocka.rent.infrastructure.mapper.JpaRoomMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class RoomRepositoryAdapter implements RoomRepository {

    private final RoomDtoRepository roomDtoRepository;
    private final JpaRoomMapper mapper;

    @Override
    public Room save(Room room) {
        RoomDbDto customerDbDto = mapper.toDto(room);
        RoomDbDto saved = roomDtoRepository.save(customerDbDto);
        return mapper.toDomain(saved);
    }

    @Override
    public void delete(Integer id) {
        roomDtoRepository.deleteById(id);
    }

    @Override
    public List<Room> findAll() {
        return roomDtoRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Room> findById(Integer id) {
        return roomDtoRepository.findById(id).map(mapper::toDomain);
    }
//
//    @Override
//    public Optional<Customer> findByNip(String nip) {
//        Optional<CustomerDbDto> dbDto = roomDtoRepository.findAllByNip(nip).stream().findFirst();
//        return dbDto.map(customerDbDto -> mapper.map(customerDbDto, Customer.class));
//    }
//
    @Override
    public Optional<Room> findByName(String name) {
        Optional<RoomDbDto> byName = roomDtoRepository.findByName(name);
        return byName.map(mapper::toDomain);
    }

}
