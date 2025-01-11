package by.tms.bookpoint.controller;

import by.tms.bookpoint.dto.AuthAccountDto;
import by.tms.bookpoint.entity.Account;
import by.tms.bookpoint.service.AccountService;
import by.tms.bookpoint.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired // чтобы не подчеркивало реализовать через конструктор (в ломбоке @RequiredArgsConstructor), пример в AccountController
    private AccountService accountService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthAccountDto dto) { //доделать, возвращать обьект User вместо простотокена
        var user = accountService.loadUserByUsername(dto.getUsername());
        if (passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            return ResponseEntity.ok(jwtUtils.generateToken((Account) user)); //какие данные (Principal) засунем в токен
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}