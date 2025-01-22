package by.tms.bookpoint.service;

import by.tms.bookpoint.dto.BookingRequestDto;
import by.tms.bookpoint.dto.BookingTimeDto;
import by.tms.bookpoint.entity.Account;
import by.tms.bookpoint.entity.Booking;
import by.tms.bookpoint.entity.Point;
import by.tms.bookpoint.repository.AccountRepository;
import by.tms.bookpoint.repository.BookingRepository;
import by.tms.bookpoint.repository.PointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PointRepository pointRepository;

    @Autowired
    private AccountRepository accountRepository;

    private final Integer MIN_BOOKING_TIME_IN_MINUTES = 60; // вынести в класс с настройками или properties

    public Booking createBooking(BookingRequestDto booking){

//        // Проверяем существование рабочего места
//        Point point = pointRepository.findById(booking.getPoint().getId())
//                .orElseThrow(() -> new IllegalArgumentException("Point not found"));// Проверяем существование рабочего места

        Point point = pointRepository.findById(booking.getPointId())
                .orElseThrow(() -> new IllegalArgumentException("Point not found"));

//        // Проверяем существование пользователя
//        Account account = accountRepository.findById(booking.getAccount().getId())
//                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        Account account = accountRepository.findById(booking.getAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        //проверяем есть  ли уже брони на это время
        boolean isOverlayTime = bookingRepository.existsByPointAndTimeRange(
                point.getId(), booking.getStartTime(), booking.getEndTime());
        if (isOverlayTime) {
            throw new IllegalArgumentException("Other point is already booked for the selected time range");
        }

//        // Устанавливаем связанные сущности
//        booking.setPoint(point);
//        booking.setAccount(account);

        Booking newBooking = new Booking();
        newBooking.setPoint(point);
        newBooking.setAccount(account);
        newBooking.setStartTime(booking.getStartTime());
        newBooking.setEndTime(booking.getEndTime());

        // Проверяем корректность времени бронирования
        if (booking.getStartTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Start time cannot be in the past");
        }
        if (booking.getEndTime().isBefore(booking.getStartTime())) {
            throw new IllegalArgumentException("End time must be after start time");
        }

        // добавить проверку на минимально допустимое время брони (час, день...)
        long bookingDurationInMinutes = Duration.between(booking.getStartTime(), booking.getEndTime()).toMinutes();
        if (bookingDurationInMinutes < MIN_BOOKING_TIME_IN_MINUTES) {
            throw new IllegalArgumentException("Booking duration less than minimum(" + MIN_BOOKING_TIME_IN_MINUTES + " minutes)");
        }

        //добавить проверку на количество броней в день, например не больше 2х

        // Сохраняем бронирование
//        return bookingRepository.save(booking);
        return bookingRepository.save(newBooking);
    }
}
