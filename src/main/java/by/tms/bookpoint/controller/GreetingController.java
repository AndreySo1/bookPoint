package by.tms.bookpoint.controller;

import by.tms.bookpoint.dto.GreetingRequestDto;
import by.tms.bookpoint.dto.GreetingResponseDto;
import by.tms.bookpoint.entity.Account;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/greeting")
public class GreetingController {
    @GetMapping
    public GreetingResponseDto greeting(@AuthenticationPrincipal Account account) {
        return new GreetingResponseDto("Hello " + account.getName());
    }
}
