
package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.exceptions.UnauthorizedAccessException;
import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.business.AccountService;
import com.techelevator.tenmo.business.TransferService;
import com.techelevator.tenmo.business.UserService;
import com.techelevator.tenmo.util.BasicLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.Objects;


@RestController
@PreAuthorize("hasRole('ROLE_USER')")

public class AccountController {

    AccountService accountService;
    UserService userService;
    TransferService transferService;

    @Autowired
    public AccountController(AccountService accountService, UserService userService, TransferService transferService) {
        this.accountService = accountService;
        this.userService = userService;
        this.transferService = transferService;
    }

    @GetMapping("account/{accountId}/balance")
    public BigDecimal getBalance(@PathVariable long accountId, Principal principal) {

        if (principal.getName().equals(userService.findByAccountId(accountId).getUsername())) {
            return accountService.getBalance(accountId);
        } else {
            BasicLogger.log("User: " + principal.getName() + " attempted unauthorized account access.");
            throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "Short AND stout!!");
        }
    }

    @GetMapping("account/{accountId}")
    public Account findByAccountId (@PathVariable long accountId, Principal principal) {
        if (principal.getName().equals(userService.findByAccountId(accountId).getUsername())) {
            return accountService.findByAccountId(accountId);
        } else {
            BasicLogger.log("User: " + principal.getName() + " attempted unauthorized account access.");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

    }

    @GetMapping("users")
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("user/{userId}")
    public User findByUserId (@PathVariable long userId) {
        return userService.findByUserId(userId);
    }

    @GetMapping("user/{userId}/account")
    public Account findAccountByUserId (@PathVariable long userId, Principal principal) {
        if (principal.getName().equals(userService.findByUserId(userId).getUsername())) {
            return accountService.findByUserId(userId);
        } else {
            BasicLogger.log("User: " + principal.getName() + " attempted unauthorized account access.");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/user/principal")
    public Principal findPrincipal(Principal principal) {
        return principal;
    }
}
