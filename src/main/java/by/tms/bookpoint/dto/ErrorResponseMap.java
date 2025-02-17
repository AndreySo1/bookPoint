package by.tms.bookpoint.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
public class ErrorResponseMap {
    private Map<String, List<String>> errors = new HashMap<>();
}
