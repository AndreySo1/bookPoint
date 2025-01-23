package by.tms.bookpoint.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@ToString
public class Account implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    @NotBlank
    private String name;//добавить валидацию мах мин символов

    @Column (unique = true)
    @NotNull
    @NotEmpty
    @NotBlank
    private String username;//добавить валидацию мах мин символов

    @NotNull
    @NotEmpty
    @NotBlank
    private String password;//добавить валидацию мах мин символов

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> authorities = new HashSet<>();

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings = new ArrayList<>();
}
