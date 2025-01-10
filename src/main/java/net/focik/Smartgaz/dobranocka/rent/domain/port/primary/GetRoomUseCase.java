package net.focik.Smartgaz.dobranocka.rent.domain.port.primary;


import net.focik.Smartgaz.dobranocka.rent.domain.Room;

import java.util.List;

public interface GetRoomUseCase {
    Room findById(Integer id);

    List<Room> findAll();

}
