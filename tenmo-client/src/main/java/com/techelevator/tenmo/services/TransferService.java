package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.*;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class TransferService {


    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl;

    private String authToken;

    public void setAuthToken(String authToken){
        this.authToken = authToken;
    }

    public TransferService(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Transfer sendTransfer (TransferCredentials transferCredentials) {
        HttpEntity<TransferCredentials> entity = createTransferCredentialsEntity(transferCredentials);
        Transfer newTransfer = new Transfer();
        try {
            ResponseEntity<Transfer> response = restTemplate.exchange(baseUrl + "transfer/send", HttpMethod.POST, entity, Transfer.class);
            newTransfer = response.getBody();

        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getMessage());
        }
        return newTransfer;
    }

    public Transfer requestTransfer (TransferCredentials transferCredentials) {
        HttpEntity<TransferCredentials> entity = createTransferCredentialsEntity(transferCredentials);
        Transfer requestedTransfer = new Transfer();
        try {
            ResponseEntity<Transfer> response = restTemplate.exchange(baseUrl + "transfer/request", HttpMethod.POST, entity, Transfer.class);
            requestedTransfer = response.getBody();
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getMessage());
        }
        return requestedTransfer;
    }

    public Transfer approveTransfer (long transferId) {
        Transfer approvedTransfer = new Transfer();
        try {
            ResponseEntity<Transfer> response = restTemplate.exchange(baseUrl + "transfer/approve/" + transferId, HttpMethod.PUT, createEntity(), Transfer.class);
            approvedTransfer = response.getBody();
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getMessage());
        }
        return approvedTransfer;
    }

    public Transfer rejectTransfer (long transferId) {
        Transfer rejectedTransfer = new Transfer();
        try {
            ResponseEntity<Transfer> response = restTemplate.exchange(baseUrl + "transfer/reject/" + transferId, HttpMethod.PUT, createEntity(), Transfer.class);
            rejectedTransfer = response.getBody();
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getMessage());
        }
        return rejectedTransfer;
    }

    public List<Transfer> getAllTransfersByAccountId(long accountId) {
        Transfer[] transfers = new Transfer[]{};
        try {
           ResponseEntity<Transfer[]> transferEntity = restTemplate.exchange(baseUrl + "transfer/account/" + accountId, HttpMethod.GET, createEntity(), Transfer[].class);
            transfers = transferEntity.getBody();
        } catch (RestClientException e) {
            BasicLogger.log(e.getMessage());
        }
        return Arrays.asList(transfers);
    }

    public List<Transfer> getAllSentTransfersByAccountId(long accountId) {
        Transfer[] sentTransfers = new Transfer[]{};
        try {
            ResponseEntity<Transfer[]> sentTransferEntity = restTemplate.exchange(baseUrl + "transfer/account/" + accountId + "/from", HttpMethod.GET, createEntity(), Transfer[].class);
        sentTransfers = sentTransferEntity.getBody();
        } catch (RestClientException e) {
            BasicLogger.log(e.getMessage());
        }
        return Arrays.asList(sentTransfers);
    }

    public List<Transfer> allReceivedTransfersByAccountId(long accountId) {
        Transfer[] receivedTransfers = new Transfer[]{};
        try {
            ResponseEntity<Transfer[]>  receivedTransferEntity = restTemplate.exchange(baseUrl + "transfer/account/" + accountId + "/to", HttpMethod.GET, createEntity(), Transfer[].class);
            receivedTransfers = receivedTransferEntity.getBody();
        } catch (RestClientException e) {
            BasicLogger.log(e.getMessage());
        }
        return Arrays.asList(receivedTransfers);
    }

    public List<Transfer> getAllSentTransfersByAccountIdAndStatus(long accountId,  long statusId) {
        Transfer[] transfers = new Transfer[]{};
        try {
            ResponseEntity<Transfer[]> response = restTemplate.exchange(baseUrl + "transfer/account/" + accountId + "/from/status/" + statusId, HttpMethod.GET, createEntity(), Transfer[].class);
            transfers = response.getBody();
        } catch (RestClientException e) {
            BasicLogger.log(e.getMessage());
        }
        return Arrays.asList(transfers);
    }

    public List<Transfer> getAllReceivedTransfersByAccountIdAndStatusId(long accountId, long statusId) {
        Transfer[] transfers = new Transfer[]{};
        try {
            ResponseEntity<Transfer[]> response = restTemplate.exchange(baseUrl + "transfer/account/" + accountId + "/to/status/" + statusId, HttpMethod.GET, createEntity(), Transfer[].class);
            transfers = response.getBody();
        } catch (RestClientException e) {
            BasicLogger.log(e.getMessage());
        }
        return Arrays.asList(transfers);
    }

    public Transfer getTransferByTransferId(long transferId) {
        Transfer transfer = null;
        try {
            ResponseEntity<Transfer> response = restTemplate.exchange(baseUrl + "transfer/" + transferId, HttpMethod.GET, createEntity(), Transfer.class);
            transfer = response.getBody();
        } catch (RestClientException e) {
            BasicLogger.log(e.getMessage());
        }
        return transfer;
    }

    private HttpEntity<TransferCredentials> createTransferCredentialsEntity(TransferCredentials transferCredentials) {
        HttpHeaders headers = createHeader();
        return new HttpEntity<>(transferCredentials, headers);
    }

    private HttpEntity<TransferStatus> createTransferStatusIdEntity (TransferStatus status) {
        HttpHeaders headers = createHeader();
        return new HttpEntity<>(status, headers);
    }

    private HttpEntity<Transfer> createTransferEntity(Transfer transfer) {
        HttpHeaders headers = createHeader();
        return new HttpEntity<>(transfer, headers);
    }

    private HttpHeaders createHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return headers;
    }

    private HttpEntity<Void> createEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }

}


