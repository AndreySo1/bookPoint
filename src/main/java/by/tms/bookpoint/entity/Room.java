package by.tms.bookpoint.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
public class Room { //Room (id, name,pointsб *location_info, capacity)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotNull
    @NotEmpty
    @NotBlank
    private String name; //добавить валидацию мах мин символов

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true) //orphanRemoval если удаляем из списка, удалится и из Point
    private List<Point> points = new ArrayList<>();
}
