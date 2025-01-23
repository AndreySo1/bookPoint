package by.tms.bookpoint.controller;

import by.tms.bookpoint.dto.BookingTimeDto;
import by.tms.bookpoint.dto.ErrorResponse;
import by.tms.bookpoint.entity.Point;
import by.tms.bookpoint.entity.Room;
import by.tms.bookpoint.repository.PointRepository;
import by.tms.bookpoint.repository.RoomRepository;
import by.tms.bookpoint.service.PointService;
import by.tms.bookpoint.utils.ErrorsUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Point Resource")
@RestController
@RequestMapping("/room/{roomId}/point")
public class PointController {

    @Autowired
    private PointRepository pointRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private PointService pointService;

    @Autowired
    private ErrorsUtils errorsUtils;

    // Получить список всех мест в комнате
    @Operation(summary = "Get all Points in a room")
    @GetMapping("/all")
    public ResponseEntity<List<Point>> getAllPointsByRoomId(@PathVariable("roomId") Long roomId) {
        var all = pointRepository.findAllByRoomId(roomId);
        return ResponseEntity.ok(all);
    }

    // Получить место по ID
    @Operation(summary = "Get Point by point_id and room_id")
    @GetMapping("/{id}") // лучше переделать или добавить получение по Number
    public ResponseEntity<?> getPointById(@PathVariable("roomId") Long roomId, @PathVariable("id") Long id) {
        Optional<Point> pointFromDb = pointRepository.findPointByRoomIdAndId(roomId, id);
        if (pointFromDb.isPresent()){
            return ResponseEntity.ok(pointFromDb);
        }
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Point not found"), HttpStatus.BAD_REQUEST);
    }

    // Добавить место в комнату
    @Operation(summary = "Create Points in a room")
    @PostMapping("/create")
    public ResponseEntity<?> createPoint(@PathVariable("roomId") Long roomId, @Valid @RequestBody Point point) {
        //проверить совпадение roomID из запроса и из обьекта передоваемого
        Point newPoint = pointService.createPoint(roomId, point); //v2 poinService
        return ResponseEntity.status(HttpStatus.CREATED).body(newPoint);// через ExceptionHAndler ошибка приходит в виде dtoResponseErr
    }

    /*обновить после, пока у Point нет полей которыне можно обновить, кроме number*/
//    // Обновить информацию о рабочем месте by Number
//    @PutMapping("/{number}")
//    public ResponseEntity<?> updatePointByNumber(@PathVariable("roomId") Long roomId, @PathVariable("number") Integer number, @RequestBody Point point, BindingResult bindingResult) {
//        Optional<Point> pointFromDb = pointRepository.findPointByRoomIdAndNumber(point.getRoomId(), point.getNumber());
//        if (bindingResult.hasErrors()) {
//            return new ResponseEntity<>(errorsUtils.errorsResponse(bindingResult), HttpStatus.BAD_REQUEST);
//        }
//        if (pointFromDb.isPresent()){
//            Point tempPoint = pointFromDb.get();
//            // обновить после пока у Point нет полей которыне можно обновить
//            pointRepository.save(tempPoint);
//            return ResponseEntity.ok(tempPoint);
//        }
//        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Point with this RoomId and Number not found"), HttpStatus.BAD_REQUEST);
//    }

    // Удалить рабочее место
    @Operation(summary = "Delete Point by point_number")
    @DeleteMapping("/{number}")
    public ResponseEntity<?> deleteDeskByNumber(@PathVariable("roomId") Long roomId, @PathVariable("number") Integer number) {
        Optional<Point> pointFromDb = pointRepository.findPointByRoomIdAndNumber(roomId, number);
        Room roomFromPoint = pointFromDb.get().getRoom();
        if (pointFromDb.isPresent()){
            pointService.deletePointByRoomAndNumber(roomFromPoint, number);
            return ResponseEntity.ok(pointFromDb);
        }
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Point not found"), HttpStatus.BAD_REQUEST);
    }

    // Проверить доступность рабочего места
    @Operation(summary = "Check availability Points by point_id")
    @PostMapping("/{pointId}/available") // можно GET и передавать время через @RequestParam ?
    public boolean checkPointAvailabilityById(@PathVariable("roomId") Long roomId, @PathVariable("pointId") Long pointId, @Valid @RequestBody BookingTimeDto bookingTimeDto) {
        return pointService.isAvailablePoint(roomId, pointId, bookingTimeDto);
    }
}
