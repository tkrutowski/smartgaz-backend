package net.focik.Smartgaz.dobranocka.rent.domain;

import lombok.AllArgsConstructor;
import net.focik.Smartgaz.dobranocka.rent.domain.port.primary.AddRoomUseCase;
import net.focik.Smartgaz.dobranocka.rent.domain.port.primary.DeleteRoomUseCase;
import net.focik.Smartgaz.dobranocka.rent.domain.port.primary.GetRoomUseCase;
import net.focik.Smartgaz.dobranocka.rent.domain.port.primary.UpdateRoomUseCase;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class RentFacade implements AddRoomUseCase, GetRoomUseCase, UpdateRoomUseCase, DeleteRoomUseCase {

    private final RentService rentService;

    @Override
    public Room addRoom(Room room) {
        return rentService.addRoom(room);
    }

    @Override
    public Room findById(Integer id) {
        return rentService.findById(id);
    }

    @Override
    public List<Room> findAll() {
        return rentService.findAll();
    }

    @Override
    public Room updateRoom(Room room) {
        return rentService.updateRoom(room);
    }

    @Override
    public void deleteRoom(Integer id) {
        rentService.deleteRoom(id);
    }
}
