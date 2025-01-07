package by.tms.bookpoint.service;

import by.tms.bookpoint.entity.Account;
import by.tms.bookpoint.entity.Role;
import by.tms.bookpoint.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Account create(Account account) {
        account.getAuthorities().add(Role.ROLE_USER);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepository.save(account);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var byUsername = accountRepository.findByUsername(username);
        if (byUsername.isPresent()) {
            return byUsername.get();
        }
        throw new UsernameNotFoundException(username);
    }
}
