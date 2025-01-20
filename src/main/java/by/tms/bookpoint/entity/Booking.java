package by.tms.bookpoint.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Setter
@Getter
@ToString
public class Booking {  //Booking (id, pointId, accountId, *date, startTime, endTime).

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "desk_id", nullable = false)
    private Point point;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

//    private LocalDateTime date;

    @Future
    @Column(nullable = false)
    private LocalDateTime startTime;

    @Future
    @Column(nullable = false)
    private LocalDateTime endTime;
}
