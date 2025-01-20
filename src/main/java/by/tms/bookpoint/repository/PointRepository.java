package by.tms.bookpoint.repository;

import by.tms.bookpoint.entity.Point;
import by.tms.bookpoint.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PointRepository extends JpaRepository<Point, Long> {
    Optional<Point> findByNumber(Integer number);

    List<Point> findAllByRoomId(Long roomId);

    Optional<Point> findPointByRoomIdAndId(Long roomId, Long id);

    Optional<Point> findPointByRoomIdAndNumber(Long roomId, Integer number);

    Optional<Point> findPointByRoomAndNumber(Room room, Integer number);

    Optional<Point> findByRoomAndNumber(Room room, Integer number);

    Optional<Point> deletePointByRoomIdAndNumber(Long roomId, Integer number);
    Optional<Point> deleteByRoomAndNumber(Room room, Integer number);
}
