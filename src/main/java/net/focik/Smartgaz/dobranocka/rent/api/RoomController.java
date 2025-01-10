package net.focik.Smartgaz.dobranocka.rent.api;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.focik.Smartgaz.dobranocka.customer.api.dto.CustomerDto;
import net.focik.Smartgaz.dobranocka.rent.api.dto.RoomDto;
import net.focik.Smartgaz.dobranocka.rent.api.mapper.ApiRoomMapper;
import net.focik.Smartgaz.dobranocka.rent.domain.Room;
import net.focik.Smartgaz.dobranocka.rent.domain.port.primary.AddRoomUseCase;
import net.focik.Smartgaz.dobranocka.rent.domain.port.primary.DeleteRoomUseCase;
import net.focik.Smartgaz.dobranocka.rent.domain.port.primary.GetRoomUseCase;
import net.focik.Smartgaz.dobranocka.rent.domain.port.primary.UpdateRoomUseCase;
import net.focik.Smartgaz.utils.exceptions.ExceptionHandling;
import net.focik.Smartgaz.utils.exceptions.HttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/dobranocka/room")
//@CrossOrigin
public class RoomController extends ExceptionHandling {

    private final ApiRoomMapper mapper;
    private final AddRoomUseCase addRoomUseCase;
    private final GetRoomUseCase getRoomUseCase;
    private final UpdateRoomUseCase updateRoomUseCase;
    private final DeleteRoomUseCase deleteRoomUseCase;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('DOBRANOCKA_ROOM_READ_ALL','DOBRANOCKA_ROOM_READ') or hasRole('ROLE_ADMIN')")
    ResponseEntity<RoomDto> getById(@PathVariable int id) {
        log.info("Request to get room with id: {}.",id);
        Room room = getRoomUseCase.findById(id);
        if (room == null) {
            log.warn("No room found.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        log.info("Room found: {}", room);
        RoomDto dto = mapper.toDto(room);
        log.debug("Mapped to Room dto: {}", dto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('DOBRANOCKA_ROOM_READ_ALL','DOBRANOCKA_ROOM_READ') or hasRole('ROLE_ADMIN')")
    ResponseEntity<List<RoomDto>> getAllCustomers() {
        log.info("Request to get rooms.");

        List<Room> roomList = getRoomUseCase.findAll();

        if (roomList.isEmpty()) {
            log.warn("No rooms found.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        log.info("Found {} rooms.", roomList.size());
        return new ResponseEntity<>(roomList.stream()
                .peek(room -> log.debug("Found room {}", room))
                .map(mapper::toDto)
                .peek(roomDto -> log.debug("Mapped found room {}", roomDto))
                .toList(), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('DOBRANOCKA_ROOM_WRITE_ALL','DOBRANOCKA_ROOM_WRITE') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<RoomDto> addRoom(@RequestBody RoomDto roomDto) {
        log.info("Request to add a new room received with data: {}", roomDto);

        Room roomToAdd = mapper.toDomain(roomDto);
        log.debug("Mapped Room DTO to domain object: {}", roomToAdd);

        Room addedRoom = addRoomUseCase.addRoom(roomToAdd);
        log.info("Room added successfully: {}", addedRoom);

        log.info(addedRoom.getId() > 0 ? "Room added with id = " + addedRoom.getId() : "No room added!");

        RoomDto dto = mapper.toDto(addedRoom);
        log.debug("Mapped Room DTO to domain object: {}", dto);

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('DOBRANOCKA_ROOM_WRITE_ALL','DOBRANOCKA_ROOM_WRITE') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<RoomDto> updateCustomer(@RequestBody RoomDto roomDto) {
        log.info("Request to edit a room received with data: {}", roomDto);

        Room roomToUpdate = mapper.toDomain(roomDto);
        log.debug("Mapped Room DTO to domain object: {}", roomToUpdate);

        Room updatedRoom = updateRoomUseCase.updateRoom(roomToUpdate);
        log.info("Room updated successfully: {}", updatedRoom);

        RoomDto dto = mapper.toDto(updatedRoom);
        log.debug("Mapped Room DTO to domain object: {}", dto);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('DOBRANOCKA_ROOM_DELETE_ALL','DOBRANOCKA_ROOM_DELETE') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<HttpResponse> deleteRoom(@PathVariable int id) {
        log.info("Request to delete ROOM with id: {}", id);

        deleteRoomUseCase.deleteRoom(id);

        log.info("Room with id: {} deleted successfully", id);

        return response(HttpStatus.NO_CONTENT, "Pokój usunięty.");
    }

    private ResponseEntity<HttpResponse> response(HttpStatus status, String message) {
        HttpResponse body = new HttpResponse(status.value(), status, status.getReasonPhrase(), message);
        return new ResponseEntity<>(body, status);
    }
}
