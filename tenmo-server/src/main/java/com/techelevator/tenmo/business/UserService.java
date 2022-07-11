package com.techelevator.tenmo.business;

import com.techelevator.tenmo.dao.AccountRepository;
import com.techelevator.tenmo.dao.UserRepository;
import com.techelevator.tenmo.model.Authority;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.util.BasicLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;


@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    AccountRepository accountRepository;

    private final BigDecimal STARTING_BALANCE = new BigDecimal("1000.00");

    public User findByUserId(long userId) {
        return userRepository.findById(userId);
    }

    public User findByAccountId(long accountId) {
        return userRepository.findByAccountId(accountId);
    }

    public User findByUsername(String username) throws UsernameNotFoundException{
        try {
            return userRepository.findByUsername(username);
        } catch (Exception e) {
            throw new UsernameNotFoundException("User '" + username + "' was not found.");
        }
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    /* We recreated the create user and create account methods using JPA repository's save */

    public boolean create(String username, String password) {
        User newUser = new User();
        String password_hash = new BCryptPasswordEncoder().encode(password);
        newUser.setUsername(username);
        newUser.setPassword(password_hash);
        try {
    /* Using saveAndFlush allowed us to get updated information more immediately,
    without pulling from the repository again */
            userRepository.saveAndFlush(newUser);
        } catch (Exception e) {
            BasicLogger.log(e.getMessage());
            return false;
        }
        Account newAccount = new Account();
        newAccount.setId(newAccount.getId());
        newAccount.setUserId(newUser.getId());
        newAccount.setBalance(STARTING_BALANCE);
        try {
            accountRepository.saveAndFlush(newAccount);
        } catch (Exception e) {
            BasicLogger.log(e.getMessage());
            return false;
        }
        return true;
    }
}
