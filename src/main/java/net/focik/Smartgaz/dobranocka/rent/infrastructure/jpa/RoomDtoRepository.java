package net.focik.Smartgaz.dobranocka.rent.infrastructure.jpa;

import net.focik.Smartgaz.dobranocka.rent.infrastructure.dto.RoomDbDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

interface RoomDtoRepository extends JpaRepository<RoomDbDto, Integer> {

    Optional<RoomDbDto> findByName(String name);

    @Query("SELECT r FROM RoomDbDto r JOIN r.beds b WHERE b.id = :bedId")
    Optional<RoomDbDto> findRoomByBedId(@Param("bedId") Integer bedId);

}
