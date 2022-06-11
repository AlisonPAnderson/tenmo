package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.*;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransferService {


    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl;

    public TransferService(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Transfer createTransfer (TransferDetails details) {
        HttpEntity<TransferDetails> entity = createDetailsEntity(details);
        Transfer newTransfer = new Transfer();

        try {
            ResponseEntity<Transfer> response = restTemplate.exchange(baseUrl + "transfer", HttpMethod.PUT, entity, Transfer.class);
            newTransfer = response.getBody();
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getMessage());
        }
        return newTransfer;
    }

    public List<User> getUsers(long id) {

        Account account = null;
        ResponseEntity<User> response = null;


        try {
            response = restTemplate.exchange(baseUrl + "/balance", entity, User.class );

            // account = restTemplate.getForObject(baseUrl + "/" + id +"/balance", Account.class);
        } catch (Exception e) {
            BasicLogger.log(e.getMessage());
        }
        return balance;
    }

    private HttpEntity<TransferDetails> createDetailsEntity(TransferDetails details) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(details, headers);
    }
}


