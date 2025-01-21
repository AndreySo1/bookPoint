package by.tms.bookpoint.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequestDto {

    @NotNull
    private Long pointId;

    @NotNull
    private Long accountId;

    @Future
    private LocalDateTime startTime;

    @Future
    private LocalDateTime endTime;
}
