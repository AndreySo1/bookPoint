package by.tms.bookpoint.service;

import by.tms.bookpoint.entity.Account;
import by.tms.bookpoint.entity.Role;
import by.tms.bookpoint.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    public Account create(Account account) {
        account.getAuthorities().add(Role.ROLE_USER);
        account.setPassword(new BCryptPasswordEncoder(11).encode(account.getPassword()));
        return accountRepository.save(account);
    }

//    @Override // v1
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        var byUsername = accountRepository.findByUsername(username);
//        if (byUsername.isPresent()) {
//            return byUsername.get();// simple
//        }
//        throw new UsernameNotFoundException(username);
//    }

    @Override //v2
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var byUsername = accountRepository.findByUsername(username);
        if (byUsername.isPresent()) {
            var acc = byUsername.get();
            return User
                    .withUsername(acc.getUsername())
                    .password(acc.getPassword())
                    .roles("USER")
                    .build();
        }
        throw new UsernameNotFoundException(username);
    }
}
