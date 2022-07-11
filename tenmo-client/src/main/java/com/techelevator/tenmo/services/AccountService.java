package com.techelevator.tenmo.services;

import com.techelevator.tenmo.App;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class  AccountService {


    public AccountService(String url) { this.baseUrl = url;}
    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl;
    private String authToken;
    public void setAuthToken(String authToken){
        this.authToken = authToken;
    }


    public BigDecimal getBalance(long accountId) {
        BigDecimal balance = null;
        ResponseEntity<BigDecimal> response;
        try {
          response = restTemplate.exchange(baseUrl + "account/" + accountId + "/balance", HttpMethod.GET, createEntity(),  BigDecimal.class );
          balance = response.getBody();

        } catch (Exception e) {
            BasicLogger.log(e.getMessage());
        }
        return balance;
    }

    public List<User> getAllUsers() {
        User[] users = new User[]{};
        try {
           ResponseEntity<User[]> userEntity = restTemplate.exchange(baseUrl + "users", HttpMethod.GET, createEntity(), User[].class);
           users = userEntity.getBody();
        } catch (RestClientException e) {
            BasicLogger.log(e.getMessage());
        }
        return Arrays.asList(users);
    }

    public Account getAccountByAccountId (long accountId) {
        Account account = new Account();
        try {
           ResponseEntity<Account> accountEntity = restTemplate.exchange(baseUrl + "account/" + accountId, HttpMethod.GET, createEntity(), Account.class);
          account = accountEntity.getBody();
        } catch (RestClientException e) {
            BasicLogger.log(e.getMessage());
        }
        return account;
    }

    public Account getAccountByUserId (long userId) {
        Account account = new Account();
        try {
            ResponseEntity<Account> accountEntity = restTemplate.exchange(baseUrl + "user/" + userId + "/account", HttpMethod.GET, createEntity(), Account.class);
            account = accountEntity.getBody();
        } catch (RestClientException e) {
            BasicLogger.log(e.getMessage());
        }
        return account;
    }

    public User getUserByUserId (long userId) {
        User user = new User();
        try {
            ResponseEntity<User> userEntity =  restTemplate.exchange(baseUrl + "user/" + userId, HttpMethod.GET, createEntity(), User.class);
            user = userEntity.getBody();
        } catch (RestClientException e) {
            BasicLogger.log(e.getMessage());
        }
        return user;
    }

    private HttpHeaders createHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return headers;
    }

    private HttpEntity createEntity() {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<String>(headers);
    }


}
