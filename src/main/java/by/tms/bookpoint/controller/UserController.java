package by.tms.bookpoint.controller;

import by.tms.bookpoint.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    private final List<User> users = new ArrayList<>();

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        user.setId(UUID.randomUUID());
        users.add(user);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users1 = users;
        return ResponseEntity.ok(users1);
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable("username") String username) {
        for (User user : users) {
            if(user.getUsername().equals(username)) {
                return ResponseEntity.ok(user);
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping
    public ResponseEntity<User> deleteUser(@RequestBody User user) {
        users.remove(user);
        return ResponseEntity.ok(user);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        for (User user1 : users) {
            if(user1.getId().equals(user.getId())) {
                user1.setName(user.getName());
                user1.setUsername(user.getUsername());
                user1.setPassword(user.getPassword());
                return ResponseEntity.ok(user1);
            }
        }
        return ResponseEntity.badRequest().build();
    }
}

