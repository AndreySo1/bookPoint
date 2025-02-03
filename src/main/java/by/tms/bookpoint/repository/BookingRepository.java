package by.tms.bookpoint.repository;

import by.tms.bookpoint.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    // //v2
    @Query("SELECT COUNT(b) > 0 FROM Booking b " +
            "WHERE b.point.id = :pointId " +
            "AND ((:startTime < b.endTime AND :endTime > b.startTime))") //попробовать возможно надо скобки перед и после последнего AND
    boolean existsByPointAndTimeRange(@Param("pointId") Long pointId,
                                     @Param("startTime") LocalDateTime startTime,
                                     @Param("endTime") LocalDateTime endTime);
}
