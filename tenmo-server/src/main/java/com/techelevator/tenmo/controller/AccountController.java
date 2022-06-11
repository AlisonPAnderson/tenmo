package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.business.AccountService;
import com.techelevator.tenmo.business.TransferService;
import com.techelevator.tenmo.business.UserService;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;
import com.techelevator.tenmo.model.TransferStatus;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
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

    @GetMapping("/{id}/balance")
    public BigDecimal getBalance(@PathVariable long id) {
        return accountService.getBalance(id);

        //return accountService.getBalance(id);
    }

    /* returns Transfer object. might be useful for printing transfer status */
    @PostMapping("/transfer/send")
    public Transfer sendTransfer(@RequestBody TransferDTO transferDetails) {
        Transfer transfer = transferService.createTransfer(transferDetails);
        return transferService.sendTransfer(transfer);
    }

    @PostMapping("/transfer/accept")
    public Transfer acceptTransfer(@RequestBody Transfer pendingTransfer) {
        return transferService.sendTransfer(pendingTransfer);
    }

    @GetMapping("/{id}/transfer")
    public List<Transfer> allTransfersByAccountId(@PathVariable long id) {
        return transferService.findAllByAccountId(id);
    }

    @GetMapping("/transfer/{id}")
    public Optional<Transfer> getTransferByTransferId(@PathVariable long id) {
        return transferService.findByTransferId(id);
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @PostMapping("/transfer/request")
    public Transfer requestTransfer(@RequestBody TransferDTO transferDetails) {
        Transfer transfer = transferService.createTransfer(transferDetails);
        return transferService.requestTransfer(transfer);
    }

    @GetMapping("/{id}/transfer/status")
    public List<Transfer> transfersByStatusAndUser (@PathVariable long id, @RequestBody TransferStatus transferStatus) {
        return transferService.findAllByStatusAndUser(transferStatus.getTransferStatusId(), id);
    }


//
//    @PostMapping("/transfer/accept")
//    public Transfer acceptTransfer(@RequestBody TransferDTO transferDetails) {
//        Transfer transfer = transferService.createTransfer(transferDetails);
//        return transferService.sendTransfer(transfer);
//    }

//    @PostMapping("/{id}/balance")
//    public BigDecimal setBalance(@PathVariable long id, @RequestBody TransferDTO transferDetails) {
//        return accountService.setBalance(id, transferDetails);
//    }
}
