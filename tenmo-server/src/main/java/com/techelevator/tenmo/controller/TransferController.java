package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.business.AccountService;
import com.techelevator.tenmo.business.TransferService;
import com.techelevator.tenmo.business.UserService;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;
import com.techelevator.tenmo.model.TransferStatus;
import com.techelevator.tenmo.util.BasicLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("transfer/")
@PreAuthorize("isAuthenticated()")
public class TransferController {

    AccountService accountService;
    TransferService transferService;
    UserService userService;

    @Autowired
    public TransferController(AccountService accountService, TransferService transferService, UserService userService) {
        this.accountService = accountService;
        this.transferService = transferService;
        this.userService = userService;
    }

    @PostMapping("send")
    public Transfer sendTransfer(@RequestBody TransferDTO transferDetails, Principal principal) {
        Transfer transfer = transferService.createTransfer(transferDetails, principal.getName());
        return transferService.sendTransfer(transfer, principal.getName());
    }

    @PostMapping("request")
    public Transfer requestTransfer(@RequestBody TransferDTO transferDetails, Principal principal) {
        Transfer transfer = transferService.createTransfer(transferDetails, principal.getName());
        return transferService.requestTransfer(transfer);
    }

    @PutMapping("approve/{transferId}")
    public Transfer approveTransfer(@PathVariable long transferId, Principal principal) {
        try {
            Transfer pendingTransfer = transferService.findById(transferId);
            if (pendingTransfer.getTransferStatus() == TransferStatus.pending) {
                return transferService.approveTransfer(pendingTransfer, principal.getName());
            } else {
                BasicLogger.log("User: " + principal.getName() + " attempted to accept non-pending transfer.");
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
        } catch (NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("reject/{transferId}")
    public Transfer rejectTransfer(@PathVariable long transferId, Principal principal) {
        try {
            Transfer pendingTransfer = transferService.findById(transferId);
            if (pendingTransfer.getTransferStatus() == TransferStatus.pending) {
                return transferService.rejectTransfer(pendingTransfer, principal.getName());
            } else {
                BasicLogger.log("User: " + principal.getName() + " attempted to reject non-pending transfer.");
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
        } catch (NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("account/{accountId}")
    public List<Transfer> allTransfersByAccountId(@PathVariable long accountId, Principal principal) {
        try {
            if (principal.getName().equals(userService.findByAccountId(accountId).getUsername())) {
                return transferService.findAllByAccountId(accountId);
            } else {
                BasicLogger.log("User: " + principal.getName() + " attempted unauthorized transfer information access.");
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
        } catch (NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("account/{accountId}/from")
    public List<Transfer> allSentTransfersByAccountId(@PathVariable long accountId, Principal principal) {
        try {
            if (principal.getName().equals(userService.findByAccountId(accountId).getUsername())) {
                return transferService.findAllByAccountFrom(accountId);
            } else {
                BasicLogger.log("User: " + principal.getName() + " attempted unauthorized transfer information access.");
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
        } catch (NullPointerException e) {
            BasicLogger.log(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("account/{accountId}/to")
    public List<Transfer> allReceivedTransfersByAccountId(@PathVariable long accountId, Principal principal) {
        try {
            if (principal.getName().equals(userService.findByAccountId(accountId).getUsername())) {
                return transferService.findAllByAccountTo(accountId);
            } else {
                BasicLogger.log("User: " + principal.getName() + " attempted unauthorized transfer information access.");
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
        } catch (NullPointerException e) {
            BasicLogger.log(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    //<editor-fold desc="Old code using transfer status ID">
/*    @GetMapping("account/{accountId}/from/status/{statusId}")
    public List<Transfer> allSentTransfersByAccountIdAndStatus(@PathVariable long accountId,
                                                               @PathVariable long statusId) {
        return transferService.findAllByAccountFromAndTransferStatusId(accountId, statusId);
    }*/

/*    @GetMapping("account/{accountId}/to/status/{statusId}")
    public List<Transfer> allReceivedTransfersByAccountIdAndStatus(@PathVariable long accountId,
                                                                   @PathVariable long statusId) {
        return transferService.findAllByAccountToAndTransferStatusId(accountId, statusId);
    }*/
    //</editor-fold>

    @GetMapping("{transferId}")
    public Transfer getTransferByTransferId(@PathVariable long transferId, Principal principal) {
        try {
            if (principal.getName().equals(userService.findByAccountId(transferService.findById(transferId).getAccountIdFrom()).getUsername())
                    || principal.getName().equals(userService.findByAccountId(transferService.findById(transferId).getAccountIdTo()).getUsername())) {
                return transferService.findById(transferId);
            } else {
                BasicLogger.log("User: " + principal.getName() + " attempted unauthorized transfer information access.");
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
        } catch (NullPointerException e) {
            BasicLogger.log(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

}
