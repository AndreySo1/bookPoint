package by.tms.bookpoint.service;

import by.tms.bookpoint.entity.Point;
import by.tms.bookpoint.entity.Room;
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

    public Point createPoint(Long roomId, Point point) {

        // Ищем Room по ID
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        //проверяем есть ли в Room такой number
        Optional<Point> pointInDb = pointRepository.findByRoomAndNumber(room, point.getNumber());
        if (pointInDb.isPresent()) {
            throw new RuntimeException("Point with this number already exists in the room");
        }

//        // Создаём Point //v2
//        Point point2 = new Point();
//        point2.setNumber(point.getNumber());
//        point2.setRoom(room);

//         Устанавливаем value в Point
        point.setRoom(room);

        return pointRepository.save(point); //v1
    }

    @Transactional
    public void deletePointByRoomAndNumber(Room room, Integer number) {
        pointRepository.deleteByRoomAndNumber(room, number);
    }
}
