package net.focik.Smartgaz.dobranocka.rent.infrastructure.jpa;

import lombok.AllArgsConstructor;
import net.focik.Smartgaz.dobranocka.rent.domain.Room;
import net.focik.Smartgaz.dobranocka.rent.domain.port.secondary.RoomRepository;
import net.focik.Smartgaz.dobranocka.rent.infrastructure.dto.RoomDbDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class RoomRepositoryAdapter implements RoomRepository {

    private final RoomDtoRepository roomDtoRepository;
    private final ModelMapper mapper;

    @Override
    public Room save(Room room) {
        RoomDbDto roomDbDto = mapper.map(room, RoomDbDto.class);
        if (roomDbDto.getBeds() != null) {
            roomDbDto.getBeds().forEach(bed -> bed.setRoom(roomDbDto));
        }
        RoomDbDto saved = roomDtoRepository.save(roomDbDto);
        return mapper.map(saved, Room.class);
    }

    @Override
    public void delete(Integer id) {
        roomDtoRepository.deleteById(id);
    }

    @Override
    public List<Room> findAll() {
        return roomDtoRepository.findAll().stream()
                .peek(roomDbDto -> System.out.println(roomDbDto.toString()))
                .map(roomDbDto -> mapper.map(roomDbDto, Room.class))
                .toList();
    }

    @Override
    public Optional<Room> findById(Integer id) {
        return roomDtoRepository.findById(id).map(roomDbDto -> mapper.map(roomDbDto, Room.class));
    }

    @Override
    public Optional<Room> findByBedId(Integer id) {
        Optional<RoomDbDto> byName = roomDtoRepository.findRoomByBedId(id);
        return byName.map(roomDbDto -> mapper.map(roomDbDto, Room.class));
    }

    @Override
    public Optional<Room> findByName(String name) {
        Optional<RoomDbDto> byName = roomDtoRepository.findByName(name);
        return byName.map(roomDbDto -> mapper.map(roomDbDto, Room.class));
    }

}
