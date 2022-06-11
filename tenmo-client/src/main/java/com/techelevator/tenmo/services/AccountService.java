package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;

@Service
public class  AccountService {


    public AccountService(String url) { this.baseUrl = url;}

    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl;

    public BigDecimal getBalance(long id) {
        BigDecimal balance = null;
        Account account = null;
        ResponseEntity<BigDecimal> response = null;
        try {
          response = restTemplate.getForEntity(baseUrl + '/' + id + "/balance", BigDecimal.class );
            balance = response.getBody();
           // account = restTemplate.getForObject(baseUrl + "/" + id +"/balance", Account.class);
        } catch (Exception e) {
            BasicLogger.log(e.getMessage());
        }
        return balance;
    }


}
