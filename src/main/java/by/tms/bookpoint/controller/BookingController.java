package by.tms.bookpoint.controller;

import by.tms.bookpoint.dto.BookingRequestDto;
import by.tms.bookpoint.dto.ErrorResponse;
import by.tms.bookpoint.entity.Booking;
import by.tms.bookpoint.entity.Point;
import by.tms.bookpoint.entity.Room;
import by.tms.bookpoint.repository.BookingRepository;
import by.tms.bookpoint.service.BookingService;
import by.tms.bookpoint.utils.ErrorsUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Booking Resource")
@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private ErrorsUtils errorsUtils;

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
    public ResponseEntity<?> createBooking(@Valid @RequestBody BookingRequestDto booking) {
        Booking newBooking = bookingService.createBooking(booking);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBooking);
    }

    //обновить данные бронирования по id
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePointByNumber(@PathVariable("id") Long id, @Valid @RequestBody Booking newBooking, BindingResult bindingResult) {
            Optional<Booking> bookingFromDb = bookingRepository.findById(id);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(errorsUtils.errorsResponse(bindingResult), HttpStatus.BAD_REQUEST);
        }
        if (bookingFromDb.isPresent()){ // вынести в BookingService и подумать может не все разрешить менять, и как буде работать валидация пересеченияс другими bookings
            Booking tempBooking = bookingFromDb.get();
            tempBooking.setPoint(newBooking.getPoint());
            tempBooking.setAccount(newBooking.getAccount());
            tempBooking.setStartTime(newBooking.getStartTime());
            tempBooking.setEndTime(newBooking.getEndTime());
            bookingRepository.save(tempBooking);
            return ResponseEntity.ok(tempBooking);
        }
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Point with this RoomId not found"), HttpStatus.BAD_REQUEST);
    }

    // Отменить бронирование
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBookingById(@PathVariable("id") Long id) {
        Optional<Booking> bookingFromDb = bookingRepository.findById(id);
        if (bookingFromDb.isPresent()) {
            bookingRepository.deleteById(id);
            return ResponseEntity.ok(bookingFromDb);
        }
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Booking not found"), HttpStatus.BAD_REQUEST);
    }

    // отменить все бронирование по Point (место не доступно/сломано/ликвидировано)
    // отмениьт все бронования по Account (пользователь забанен/уволен)


}
