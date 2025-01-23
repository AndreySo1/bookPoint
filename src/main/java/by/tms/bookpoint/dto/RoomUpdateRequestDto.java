package by.tms.bookpoint.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RoomUpdateRequestDto {

    @Column(unique = true)
    @NotNull
    @NotEmpty
    @NotBlank
    private String name;
}
