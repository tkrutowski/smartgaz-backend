package net.focik.Smartgaz.dobranocka.rent.api.mapper;

import lombok.RequiredArgsConstructor;
import net.focik.Smartgaz.dobranocka.rent.api.dto.RoomDto;
import net.focik.Smartgaz.dobranocka.rent.domain.Room;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApiRoomMapper {
    private final ModelMapper modelMapper;

    public Room toDomain(RoomDto dto) {
        return modelMapper.map(dto, Room.class);
    }

    public RoomDto toDto(Room room) {
        return modelMapper.map(room, RoomDto.class);
    }

    public Bed toDomain(BedDto dto) {
        return modelMapper.map(dto, Bed.class);
    }

    public BedDto toDto(Bed bed) {
        return modelMapper.map(bed, BedDto.class);
    }
}