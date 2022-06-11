package com.techelevator.tenmo.business;

import com.techelevator.tenmo.dao.AccountRepository;
import com.techelevator.tenmo.dao.UserRepository;
import com.techelevator.tenmo.model.Authority;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.Account;
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



    public User findByUsername(String username) throws UsernameNotFoundException{
        try {
            //            foundUser.setActivated(true);
//            foundUser.setAuthorities("USER");
            return userRepository.findByUsername(username);
        } catch (Exception e) {
            throw new UsernameNotFoundException("User '" + username + "' was not found.");
        }
    }



    public List<User> findAll(){
        return userRepository.findAll();
    }



    public boolean create(String username, String password) {
        User newUser = new User();
        String password_hash = new BCryptPasswordEncoder().encode(password);
        newUser.setUsername(username);
        newUser.setPassword(password_hash);
        newUser.setActivated(true);
        newUser.setAuthorities("USER");
        try {
            userRepository.save(newUser);
        } catch (Exception e) {
            return false;
        }

        Account newAccount = new Account();
        newAccount.setId(newAccount.getId());
        newAccount.setUserId(newUser.getId());
        newAccount.setBalance(STARTING_BALANCE);
        try {
            accountRepository.save(newAccount);
        } catch (Exception e) {
            return false;
        }
        return true;
    }




}
