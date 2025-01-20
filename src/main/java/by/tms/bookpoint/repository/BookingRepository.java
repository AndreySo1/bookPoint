package by.tms.bookpoint.repository;

import by.tms.bookpoint.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT COUNT(b) > 0 FROM Booking b WHERE b.point.id = :deskId " +
            "AND ((:startTime BETWEEN b.startTime AND b.endTime) " +
            "OR (:endTime BETWEEN b.startTime AND b.endTime) " +
            "OR (b.startTime BETWEEN :startTime AND :endTime))")
    boolean existsByPointAndTimeRange(@Param("deskId") Long deskId,
                                     @Param("startTime") LocalDateTime startTime,
                                     @Param("endTime") LocalDateTime endTime);

    // //JPQL
//    @Query("SELECT COUNT(b) > 0 FROM Booking b " +
//            "WHERE b.point.id = :deskId " +
//            "AND ((:startTime < b.endTime AND :endTime > b.startTime))")
//    boolean existsByDeskAndTimeRange(@Param("deskId") Long deskId,
//                                     @Param("startTime") LocalDateTime startTime,
//                                     @Param("endTime") LocalDateTime endTime);
}
