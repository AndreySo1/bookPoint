package by.tms.bookpoint.controller;

import by.tms.bookpoint.dto.ErrorResponse;
import by.tms.bookpoint.entity.Point;
import by.tms.bookpoint.entity.Room;
import by.tms.bookpoint.repository.PointRepository;
import by.tms.bookpoint.repository.RoomRepository;
import by.tms.bookpoint.service.PointService;
import by.tms.bookpoint.utils.ErrorsUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    @GetMapping("/all")
    public ResponseEntity<List<Point>> getAllPointsByRoomId(@PathVariable("roomId") Long roomId) {
        var all = pointRepository.findAllByRoomId(roomId);
        return ResponseEntity.ok(all);
    }

    // Получить место по ID
    @GetMapping("/{id}") // лучше переделать или добавить получение по Number
    public ResponseEntity<?> getPointById(@PathVariable("roomId") Long roomId, @PathVariable("id") Long id) {
        Optional<Point> pointFromDb = pointRepository.findPointByRoomIdAndId(roomId, id);
        if (pointFromDb.isPresent()){
            return ResponseEntity.ok(pointFromDb);
        }
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Point not found"), HttpStatus.BAD_REQUEST);
    }

    // Добавить место в комнату
    @PostMapping("/create")
    public ResponseEntity<?> createPoint(@PathVariable("roomId") Long roomId, @Valid @RequestBody Point point) {
        //проверить совпадение roomID из запроса и из обьекта передоваемого
        Point newPoint = pointService.createPoint(roomId, point); //v2 poinService

//        Optional<Point> pointFromDb = pointRepository.findPointByRoomIdAndNumber(point.getRoomId(), point.getNumber());
//        if (pointFromDb.isPresent()){
//            return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Point with this RoomId and Number already exist"), HttpStatus.BAD_REQUEST);
//        }
//        Point newPoint = pointRepository.save(point);
//        return new ResponseEntity<>(newPoint, HttpStatus.CREATED);

        return ResponseEntity.status(HttpStatus.CREATED).body(newPoint);// сделать чтобы выводило респонс ошибки как при create room
    }

    /*обновить после пока у Point нет полей которыне можно обновить*/
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
//        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Point with this RoomId and Number already exist"), HttpStatus.BAD_REQUEST);
//    }

    // Удалить рабочее место
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

//    // Проверить доступность рабочего места
//    @GetMapping("/{deskId}/availability")
//    public boolean checkDeskAvailability(@PathVariable Long roomId, @PathVariable Long deskId,
//                                         @RequestParam String date, @RequestParam String time) {
//        // Логика для проверки доступности рабочего места
//    }
}
