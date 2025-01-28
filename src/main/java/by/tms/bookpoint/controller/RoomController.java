package by.tms.bookpoint.controller;

import by.tms.bookpoint.dto.ErrorResponse;
import by.tms.bookpoint.dto.RoomUpdateRequestDto;
import by.tms.bookpoint.entity.Room;
import by.tms.bookpoint.repository.RoomRepository;
import by.tms.bookpoint.utils.ErrorsUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Room Resource")
@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ErrorsUtils errorsUtils;

    // Получить список всех комнат
    @Operation(summary = "Get all Rooms")
    @GetMapping("/all")
    public ResponseEntity<List<Room>> getAllRooms() {
        var all = roomRepository.findAll();
        return ResponseEntity.ok(all);
    }

    // Получить конкретной комнатy
    @Operation(summary = "Get Room by room_id")
    @GetMapping("/{id}")
    public ResponseEntity<?> getRoomById(@PathVariable("id") Long id) {
        Optional<Room> roomFromDb = roomRepository.findById(id);
        if (roomFromDb.isPresent()){
            return ResponseEntity.ok(roomFromDb);
        }
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Room not found"), HttpStatus.BAD_REQUEST);
    }

    // Добавить новую комнату
    @Operation(summary = "Create Room")
    @PostMapping("/create")
    public ResponseEntity<?> createRoom(@Valid @RequestBody Room room) {
        Optional<Room> roomFromDb = roomRepository.findByName(room.getName());
        if (roomFromDb.isPresent()){
            return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Room with this Name has already exist"), HttpStatus.BAD_REQUEST);
        }
        Room newRoom = roomRepository.save(room);
        return new ResponseEntity<>(newRoom, HttpStatus.CREATED);
    }

    // Обновить информацию о комнате
    @Operation(summary = "Update room by room_id")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRoomById(@PathVariable("id") Long id, @Valid @RequestBody RoomUpdateRequestDto roomUpdate, BindingResult bindingResult) {
        Optional<Room> roomFromDb = roomRepository.findById(id);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(errorsUtils.errorsResponse(bindingResult), HttpStatus.BAD_REQUEST);
        }
        if (roomFromDb.isPresent()){
            Room tempRoom = roomFromDb.get(); //попробовать сеттить сразу в roomFromDb, будет ли работать
            tempRoom.setName(roomUpdate.getName());
            roomRepository.save(tempRoom);
            return ResponseEntity.ok(tempRoom);
        }
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Room not found"), HttpStatus.BAD_REQUEST);
    }

    // Удалить комнату
    @Operation(summary = "Delete room by room_id")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRoomById(@PathVariable("id") Long id) {
        Optional<Room> roomFromDb = roomRepository.findById(id);
        if (roomFromDb.isPresent()){
            roomRepository.deleteById(id);
            return ResponseEntity.ok(roomFromDb);
        }
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Room not found"), HttpStatus.BAD_REQUEST);
    }
}
