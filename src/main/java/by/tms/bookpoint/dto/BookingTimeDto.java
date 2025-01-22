package by.tms.bookpoint.dto;

import jakarta.validation.constraints.Future;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BookingTimeDto {

    @Future
    private LocalDateTime startTime;

    @Future
    private LocalDateTime endTime;
}
