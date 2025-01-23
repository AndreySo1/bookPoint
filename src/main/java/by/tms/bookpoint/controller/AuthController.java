package by.tms.bookpoint.controller;

import by.tms.bookpoint.dto.AuthAccountDto;
import by.tms.bookpoint.entity.Account;
import by.tms.bookpoint.service.AccountService;
import by.tms.bookpoint.utils.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Login Resource")
@RestController
@RequestMapping("/auth")
public class AuthController {

//    @Autowired // чтобы не подчеркивало реализовать через конструктор (в ломбоке @RequiredArgsConstructor), пример в AccountController
//    private AccountService accountService;//v1

    @Autowired
    AuthenticationManager authenticationManager; //v2

//    @Autowired
//    private PasswordEncoder passwordEncoder; //v1

    @Autowired
    private JwtUtils jwtUtils;



//    @PostMapping("/login") //v1
//    public ResponseEntity<String> login(@RequestBody AuthAccountDto dto) { //доделать, возвращать обьект User вместо простотокена
//        var user = accountService.loadUserByUsername(dto.getUsername());
//        if (passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
//            return ResponseEntity.ok(jwtUtils.generateToken((Account) user)); //какие данные (Principal) засунем в токен
//        }
//        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//    }

    @Operation(summary = "Login to app, return auth token (jwt)")
    @PostMapping("/login") //v2
    public String login(@RequestBody AuthAccountDto dto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return jwtUtils.generateToken(userDetails.getUsername());
    }
}