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
public class Room { //Room (id, name,points *location)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotNull
    @NotEmpty
    @NotBlank
    private String name;

    @ElementCollection(fetch = FetchType.EAGER) // настроить связи один ко многим
    private List<Point> points = new ArrayList<>();
}
