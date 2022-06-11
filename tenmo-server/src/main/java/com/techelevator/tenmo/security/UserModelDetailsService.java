package com.techelevator.tenmo.security;


//import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.business.UserService;
import com.techelevator.tenmo.dao.UserRepository;
import com.techelevator.tenmo.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class UserModelDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(UserModelDetailsService.class);

//    @Autowired
//    private final UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    public UserModelDetailsService(UserService userService) { this.userService = userService;}

    //private final UserDao userDao;

//    @Autowired
//    public UserModelDetailsService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

    @Override
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating user '{}'", login);
        String lowercaseLogin = login.toLowerCase();
        return createSpringSecurityUser(lowercaseLogin, userService.findByUsername(lowercaseLogin));
    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(String lowercaseLogin, User user) {
        if (!user.isActivated()) {
            throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
        }
        List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                grantedAuthorities);
    }
}

