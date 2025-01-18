package by.tms.bookpoint.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@Setter
@Getter
@ToString
public class Booking {  //Booking (id, roomId, accountId, date, startTime, endTime).

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    @NotBlank
    private Long roomId;

    @NotNull
    @NotEmpty
    @NotBlank
    private Long accountId;

    private Date date;

    private Date startTime;

    private Date endTime;
}
