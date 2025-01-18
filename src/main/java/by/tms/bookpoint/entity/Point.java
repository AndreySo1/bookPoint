package by.tms.bookpoint.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@ToString
public class Point { //Point (id, roomId, number, *type).

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true) // не подойдет, может быть одинаковый номер в разных комнатах
    @NotNull
//    @NotEmpty
//    @NotBlank
    private Integer number;

//    @NotNull
//    @NotEmpty
//    @NotBlank
//    @JoinColumn(name = "room_id", nullable = false) // Связываем с Room
//    private Long roomId;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false) // Связываем с Room
    @JsonIgnore //чтобы не зацикливалось в респонссе
    private Room room;
}
