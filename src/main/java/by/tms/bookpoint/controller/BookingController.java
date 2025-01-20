package by.tms.bookpoint.controller;

import by.tms.bookpoint.dto.ErrorResponse;
import by.tms.bookpoint.entity.Booking;
import by.tms.bookpoint.entity.Point;
import by.tms.bookpoint.entity.Room;
import by.tms.bookpoint.repository.BookingRepository;
import by.tms.bookpoint.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingService bookingService;

    // Получить список всех бронирований
    @GetMapping("/all")
    public ResponseEntity<List<Booking>> getAllBookings() {
        var all = bookingRepository.findAll();
        return ResponseEntity.ok(all);
    }

    // Получить бронирование по Id
    @GetMapping("/{id}")
    public ResponseEntity<?> getBookingById(@PathVariable("id") Long id) {
        Optional<Booking> bookingFromDb = bookingRepository.findById(id);
        if (bookingFromDb.isPresent()) {
            return ResponseEntity.ok(bookingFromDb);
        }
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Booking not found"), HttpStatus.BAD_REQUEST);
    }

    // Создать новое бронирование
    @PostMapping("/create")
    public ResponseEntity<?> createBooking(@Valid @RequestBody Booking booking) {
        Booking newBooking = bookingService.createBooking(booking);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBooking);
    }

    // Отменить бронирование
    @DeleteMapping("/{id}")
    public void cancelBooking(@PathVariable Long id) {
        // Логика для отмены бронирования
    }

}
