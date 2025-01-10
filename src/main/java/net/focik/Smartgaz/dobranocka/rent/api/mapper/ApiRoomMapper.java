package net.focik.Smartgaz.dobranocka.rent.api.mapper;

import net.focik.Smartgaz.dobranocka.rent.api.dto.RoomDto;
import net.focik.Smartgaz.dobranocka.rent.domain.Room;
import org.springframework.stereotype.Component;

@Component
public class ApiRoomMapper {


    public Room toDomain(RoomDto dto) {
//        valid(dto);
        return Room.builder()
                .id(dto.getId())
                .name(dto.getName())
                .color(dto.getColor())
                .bedType(dto.getBeds())
                .price(dto.getPrice())
                .info(dto.getInfo())
                .build();
    }

    public RoomDto toDto(Room room) {
        return RoomDto.builder()
                .id(room.getId())
                .name(room.getName())
                .color(room.getColor())
                .beds(room.getBedType())
                .price(room.getPrice())
                .info(room.getInfo())
                .build();
    }
//    private void valid(RoomDto dto) {
//        if (dto.getName() == 0)
//            throw new CustomerNotValidException("IdCustomer can't be null.");
//        if (dto.getInvoiceDate().isEmpty())
//            throw new InvoiceNotValidException("Date can't be empty.");
//    }
}