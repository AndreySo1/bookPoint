package by.tms.bookpoint.service;

import by.tms.bookpoint.dto.BookingTimeDto;
import by.tms.bookpoint.entity.Point;
import by.tms.bookpoint.entity.Room;
import by.tms.bookpoint.repository.BookingRepository;
import by.tms.bookpoint.repository.PointRepository;
import by.tms.bookpoint.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PointService {

    @Autowired
    private PointRepository pointRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public Point createPoint(Long roomId, Point point) {

        // Ищем Room по ID
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        //проверяем есть ли в Room такой number
        Optional<Point> pointInDb = pointRepository.findByRoomAndNumber(room, point.getNumber());
        if (pointInDb.isPresent()) {
            throw new RuntimeException("Point with this number already exists in the room");
        }

//         Устанавливаем value в Point
        point.setRoom(room);

        return pointRepository.save(point); //v1
    }

    @Transactional
    public void deletePointByRoomAndNumber(Room room, Integer number) {
        pointRepository.deleteByRoomAndNumber(room, number);
    }

    public Boolean isAvailablePoint(Long roomId, Long pointId, BookingTimeDto bookingTimeDto){

        Point point = pointRepository.findPointByRoomIdAndId(roomId, pointId)
                .orElseThrow(() -> new IllegalArgumentException("Point not found in this room"));

        boolean isOverlayTime= bookingRepository.existsByPointAndTimeRange(
                pointId, bookingTimeDto.getStartTime(), bookingTimeDto.getEndTime());

        return !isOverlayTime; //если найдены пересеения на этот интервал вернем false
    }
}
