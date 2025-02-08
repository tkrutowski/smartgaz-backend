package net.focik.Smartgaz.dobranocka.rent.domain.port.primary;


import net.focik.Smartgaz.dobranocka.rent.domain.Bed;
import net.focik.Smartgaz.dobranocka.rent.domain.Room;

public interface UpdateRoomUseCase {
    Room updateRoom(Room room);

    Bed updateBed(Bed bed);
}
