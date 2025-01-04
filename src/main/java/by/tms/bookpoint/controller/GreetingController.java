package by.tms.bookpoint.controller;

import by.tms.bookpoint.dto.GreetingRequestDto;
import by.tms.bookpoint.dto.GreetingResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/greeting")
public class GreetingController {
    @GetMapping
    public GreetingResponseDto greeting(@RequestBody GreetingRequestDto dto) {
        return new GreetingResponseDto("Hello name: " + dto.getName());
    }
}
