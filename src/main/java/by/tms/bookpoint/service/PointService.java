package by.tms.bookpoint.service;

import by.tms.bookpoint.entity.Point;
import by.tms.bookpoint.entity.Room;
import by.tms.bookpoint.repository.PointRepository;
import by.tms.bookpoint.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

//        // Создаём Point
//        Point point2 = new Point();
//        point2.setNumber(point.getNumber());
//        point2.setRoom(room);

        // Устанавливаем Room в Point
        point.setRoom(room);

        // Сохраняем Point
        return pointRepository.save(point);
    }
}
