package by.tms.bookpoint.controller;

import by.tms.bookpoint.dto.ErrorResponse;
import by.tms.bookpoint.dto.ErrorResponseMap;
import by.tms.bookpoint.repository.AccountRepository;
import by.tms.bookpoint.utils.ErrorsUtils;
import by.tms.bookpoint.utils.JwtUtils;
import by.tms.bookpoint.dto.AuthAccountDto;
import by.tms.bookpoint.entity.Account;
import by.tms.bookpoint.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Tag(name = "Account Resource")
@RestController
@RequestMapping("/account")
//@RequiredArgsConstructor //*1 генерация конструктора для всех необходимых полей
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

//    private final AccountService accountService; //*1 вместо варианта выше , ломбок генерит конструктор

    @Autowired
    private  JwtUtils jwtUtils;

    @Autowired
    private ErrorsUtils errorsUtils;

    @Operation(summary = "Find all User", description = "Find all User")
    @GetMapping("/all")
    public ResponseEntity<List<Account>> all() {
        var all = accountRepository.findAll();
        return ResponseEntity.ok(all);
    }

    //    @ApiResponse(responseCode = "200", description = "request is successfully") // maybe annotation with @ApiResponse don't need
//    @ApiResponse(responseCode = "400", description = "Staff Id not found")
    @Operation(summary = "Find User by Id", description = "Finds User by Id")
    @GetMapping("/{id}")
    public ResponseEntity<?> findStaffById(@PathVariable("id") Long id) {
        Optional<Account> accountFromDb = accountRepository.findById(id);
        if (accountFromDb.isPresent()){
            return ResponseEntity.ok(accountFromDb);
        }
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "User not found"), HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "Crate User", description = "Crate User, send request with Account object")
    @PostMapping("/create")
    public ResponseEntity<?> createAccount(@RequestBody Account account) {
        Optional<Account> accountFromDb = accountRepository.findByUsername(account.getUsername());
        if (accountFromDb.isPresent()){
            return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "User with this Username has already exist"), HttpStatus.BAD_REQUEST);
        }
        Account acc = accountService.create(account);
        return new ResponseEntity<>(acc, HttpStatus.CREATED);
    }

    @ApiResponse(responseCode = "200", description = "request is successfully")
    @ApiResponse(responseCode = "400", description = "Request JSON have fail validation or Staff Id not found")
    @Operation(summary = "Update Staff by Id", description = "Update Staff by Id, check validate Staff object and exists Id")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStaffById(@PathVariable("id") Long id, @Valid @RequestBody Account newAccount, BindingResult bindingResult) {
        Optional<Account> accountFromDb = accountRepository.findById(id);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(errorsUtils.errorsResponse(bindingResult), HttpStatus.BAD_REQUEST);
        }
        if (accountFromDb.isPresent()){ //вынести в AccountService
            Account tempAccount = accountFromDb.get();
            tempAccount.setName(newAccount.getName());
            tempAccount.setUsername(newAccount.getUsername());
            tempAccount.setPassword(newAccount.getPassword());
            accountRepository.save(tempAccount);
            return ResponseEntity.ok(tempAccount);
        }
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "User not found"), HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "Delete User by Id", description = "Delete User by Id")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStaffById(@PathVariable("id") Long id) {
        Optional<Account> accountFromDb = accountRepository.findById(id);
        if (accountFromDb.isPresent()){
            accountRepository.deleteById(id);
            return ResponseEntity.ok(accountFromDb);
        }
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "User not found"), HttpStatus.BAD_REQUEST);
    }

}
