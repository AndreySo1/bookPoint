package by.tms.bookpoint.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Staff {
//if will be change, need update PUT staffController
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String staff_id;
    private String name;
    private String surname;
    private String password;
}
