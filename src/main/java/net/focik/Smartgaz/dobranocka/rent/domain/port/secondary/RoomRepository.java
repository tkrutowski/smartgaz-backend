package net.focik.Smartgaz.dobranocka.rent.domain.port.secondary;

import net.focik.Smartgaz.dobranocka.rent.domain.Room;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface RoomRepository {

    Room save(Room room);

    void delete(Integer id);

//    List<Customer> findAll();

//    Optional<Customer> findById(Integer id);

//    Optional<Customer> findByNip(String nip);

    Optional<Room> findByName(String name);

    List<Room> findAll();

    Optional<Room> findById(Integer id);
}
