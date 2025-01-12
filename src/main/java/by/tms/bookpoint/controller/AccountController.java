package by.tms.bookpoint.controller;

import by.tms.bookpoint.utils.JwtUtils;
import by.tms.bookpoint.dto.AuthAccountDto;
import by.tms.bookpoint.entity.Account;
import by.tms.bookpoint.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor //*1 генерация конструктора для всех необходимых полей
public class AccountController {

//    @Autowired
//    AccountRepository accountRepository;

//    @Autowired
//    private AccountService accountService;

    private final AccountService accountService; //*1 вместо варианта выше , ломбок генерит конструктор

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Autowired
    private  JwtUtils jwtUtils;

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        var acc = accountService.create(account);
//        var acc =accountRepository.save(account);
        return new ResponseEntity<>(acc, HttpStatus.CREATED);
    }
}
