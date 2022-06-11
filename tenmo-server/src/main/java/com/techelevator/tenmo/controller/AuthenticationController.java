package com.techelevator.tenmo.controller;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;

import com.techelevator.tenmo.dao.AccountRepository;
import com.techelevator.tenmo.business.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.techelevator.tenmo.dao.UserRepository;
//import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.LoginDTO;
import com.techelevator.tenmo.model.RegisterUserDTO;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.security.jwt.TokenProvider;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Controller to authenticate users.
 */
@RestController
public class AuthenticationController {


    private final TokenProvider tokenProvider;

    @Autowired
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    // private final UserRepository userRepository;
    // private AccountRepository accountRepository;

    @Autowired
    private final UserService userService;
//    @Autowired
//    UserService userService;



    //private UserDao userDao;

    @Autowired
    public AuthenticationController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder, UserRepository userRepository, UserService userService) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        //this.userDao = userDao;
        this.userService = userService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public LoginResponse login(@Valid @RequestBody LoginDTO loginDto) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication, false);

        User user = userService.findByUsername(loginDto.getUsername());
        //User user = userDao.findByUsername(loginDto.getUsername());

        return new LoginResponse(jwt, user);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    //@RequestMapping(value = "/register", method = RequestMethod.POST)
    public void register(@Valid @RequestBody RegisterUserDTO newUser) {
        if(!userService.create(newUser.getUsername(), newUser.getPassword())) {
       // if (!userDao.create(newUser.getUsername(), newUser.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User registration failed.");
        }
    }

//    @PermitAll
//    @GetMapping
//    public List<User> findAll() {
//        return userService.findAll();
//    }

    @PermitAll
    @GetMapping("/{username}")
    public User findByUsername(@PathVariable String username) {
        return userService.findByUsername(username);
    }



    /**
     * Object to return as body in JWT Authentication.
     */
    static class LoginResponse {

        private String token;
        private User user;

        LoginResponse(String token, User user) {
            this.token = token;
            this.user = user;
        }

        public String getToken() {
            return token;
        }

        void setToken(String token) {
            this.token = token;
        }

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}
    }
}

