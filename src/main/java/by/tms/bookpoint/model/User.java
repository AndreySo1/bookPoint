package by.tms.bookpoint.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class User {
    private UUID id;
    private String name;
    private String username;
    private String password;
}
