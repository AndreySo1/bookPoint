package by.tms.bookpoint.service;

import by.tms.bookpoint.entity.Account;
import by.tms.bookpoint.entity.Booking;
import by.tms.bookpoint.entity.Point;
import by.tms.bookpoint.repository.AccountRepository;
import by.tms.bookpoint.repository.BookingRepository;
import by.tms.bookpoint.repository.PointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PointRepository pointRepository;

    @Autowired
    private AccountRepository accountRepository;

    public Booking createBooking(Booking booking){

        // Проверяем существование рабочего места
        Point point = pointRepository.findById(booking.getPoint().getId())
                .orElseThrow(() -> new IllegalArgumentException("Desk not found"));

        // Проверяем существование пользователя
        Account account = accountRepository.findById(booking.getAccount().getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Проверяем наличие пересечения с другими бронированиями
        boolean isOverlapping = bookingRepository.existsByPointAndTimeRange(
                point.getId(), booking.getStartTime(), booking.getEndTime());
        if (isOverlapping) {
            throw new IllegalArgumentException("Desk is already booked for the selected time range");
        }

        //JPQL
//        boolean isOverlapping = bookingRepository.existsByPointAndTimeRange(deskId, startTime, endTime);
//        if (isOverlapping) {
//            throw new IllegalArgumentException("Desk is already booked for the selected time range");
//        }

        // Устанавливаем связанные сущности
        booking.setPoint(point);
        booking.setAccount(account);

        // Проверяем корректность времени бронирования
        if (booking.getStartTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Start time cannot be in the past");
        }
        if (booking.getEndTime().isBefore(booking.getStartTime())) {
            throw new IllegalArgumentException("End time must be after start time");
        }
        // добавить проверку на минимальное время

        // Сохраняем бронирование
        return bookingRepository.save(booking);
    }
}
