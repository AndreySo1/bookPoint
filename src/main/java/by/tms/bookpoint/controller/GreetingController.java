package by.tms.bookpoint.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Test Greeting Resource")
@RestController
@RequestMapping("/greeting")
public class GreetingController {

    @GetMapping
//    @RolesAllowed("ADMIN") // почитать про аннотацию
    public ResponseEntity<String> greeting(@RequestParam("name") String name) {
        String message = "Hello %s".formatted(name);
        return ResponseEntity.ok(message);
    }
}
