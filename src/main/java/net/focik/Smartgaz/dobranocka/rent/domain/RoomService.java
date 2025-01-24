package net.focik.Smartgaz.dobranocka.rent.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.focik.Smartgaz.dobranocka.rent.domain.exception.RoomAlreadyExistException;
import net.focik.Smartgaz.dobranocka.rent.domain.exception.RoomNotFoundException;
import net.focik.Smartgaz.dobranocka.rent.domain.port.secondary.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
class RoomService {

    private final RoomRepository roomRepository;

    public Room addRoom(Room room) {
        validate(room);
        return roomRepository.save(room);
    }

    public Room updateRoom(Room room) {
        findById(room.getId());
        return roomRepository.save(room);
    }

    public List<Bed> findAllBeds() {
        return findAll().stream()
                .map(Room::getBeds)
                .flatMap(List::stream)
                .toList();
    }

    public Room findById(Integer id) {
        Optional<Room> byId = roomRepository.findById(id);
        if (byId.isEmpty()) {
            throw new RoomNotFoundException("id", String.valueOf(id));
        }
        return byId.get();
    }

    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    public void deleteRoom(Integer id) {
        roomRepository.delete(id);
    }

    private void validate(Room room) {
        Optional<Room> byName = roomRepository.findByName(room.getName());
        if (byName.isPresent()) {
            throw new RoomAlreadyExistException("Room with NAME: " + room.getName() + " already exists");
        }
    }

}
