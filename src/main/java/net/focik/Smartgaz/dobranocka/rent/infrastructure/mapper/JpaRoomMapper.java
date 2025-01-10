package net.focik.Smartgaz.dobranocka.rent.infrastructure.mapper;

import net.focik.Smartgaz.dobranocka.rent.domain.BedType;
import net.focik.Smartgaz.dobranocka.rent.domain.Room;
import net.focik.Smartgaz.dobranocka.rent.infrastructure.dto.RoomDbDto;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class JpaRoomMapper {

    public RoomDbDto toDto(Room room) {
        return RoomDbDto.builder()
                .id(room.getId())
                .name(room.getName())
                .color(room.getColor())
                .beds(room.getBedType().stream().map(Enum::name).collect(Collectors.joining(",")))
                .price(room.getPrice())
                .info(room.getInfo())
                .build();
    }

    public Room toDomain(RoomDbDto dto) {
        return Room.builder()
                .id(dto.getId())
                .name(dto.getName())
                .color(dto.getColor())
                .bedType(Arrays.stream(dto.getBeds().split(","))
                .map(BedType::valueOf)
                .collect(Collectors.toList()))
                .price(dto.getPrice())
                .info(dto.getInfo())
                .build();
    }
}