package by.tms.bookpoint.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@ToString
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"room_id", "number"})) // уникальность свзяки полей
public class Point { //Point (id, roomId, number, *type).

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Integer number;//добавить валидацию мах символов, только цифры

    @ManyToOne//v2
    @JoinColumn(name = "room_id", nullable = false)
    @JsonIgnore //чтобы не зацикливалось в респонссе
    private Room room;

    @OneToMany(mappedBy = "point", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings = new ArrayList<>();
}
