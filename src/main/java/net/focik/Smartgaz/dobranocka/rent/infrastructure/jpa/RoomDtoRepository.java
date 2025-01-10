package net.focik.Smartgaz.dobranocka.rent.infrastructure.jpa;

import net.focik.Smartgaz.dobranocka.rent.infrastructure.dto.RoomDbDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface RoomDtoRepository extends JpaRepository<RoomDbDto, Integer> {

    Optional<RoomDbDto> findByName(String name);
}
