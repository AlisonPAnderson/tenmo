package com.techelevator.tenmo.business;

import com.techelevator.tenmo.dao.AccountRepository;
import com.techelevator.tenmo.dao.TransferRepository;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class TransferService {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AccountService accountService;
    @Autowired
    TransferRepository transferRepository;

    private final long STATUS_PENDING = 1;
    private final long STATUS_APPROVED = 2;
    private final long STATUS_REJECTED = 3;

    private final long TYPE_REQUEST = 1;
    private final long TYPE_SENDING = 2;



    /* changed name to Send Transfer in case we implement Request Transfer later
    *  added TransferDTO to receive transfer details */

    public Transfer createTransfer(TransferDTO transferDetails) {
        if (transferDetails.getAmount().compareTo(BigDecimal.valueOf(0)) <= 0) {
            //TODO Invalid Transfer amount
        }
        if(!accountRepository.findAll().contains(accountRepository.findByUserId(transferDetails.getFromID()))) {
            //TODO add User Not Found Exception
        }
        if(!accountRepository.findAll().contains(accountRepository.findByUserId(transferDetails.getToID()))) {
            //TODO add User Not Found Exception
        }
        if (transferDetails.getFromID() == transferDetails.getToID()) {
            //TODO add Invalid Recipient Exception
        }
        Transfer newTransfer = new Transfer();
        newTransfer.setAccountFrom(accountRepository.findByUserId(transferDetails.getFromID()).getId());
        newTransfer.setAccountTo(accountRepository.findByUserId(transferDetails.getToID()).getId());
        newTransfer.setAmount(transferDetails.getAmount());

        return newTransfer;
    }

    public Transfer sendTransfer (Transfer transfer) {
//        long fromId = transferDetails.getFromID();
//        long toId = transferDetails.getToID();
//        BigDecimal transferAmount = transferDetails.getAmount();

        Account fromAccount = accountRepository.findById(transfer.getAccountFrom());
        Account toAccount = accountRepository.findById(transfer.getAccountTo());

        BigDecimal newFromBalance = accountService.subtractFromBalance(fromAccount.getUserId(), transfer.getAmount());
        BigDecimal newToBalance = accountService.addToBalance(toAccount.getUserId(), transfer.getAmount());

        accountService.updateBalance(toAccount.getUserId(), newToBalance);
        accountService.updateBalance(fromAccount.getUserId(), newFromBalance);
//        toAccount.setBalance(toAccount.getBalance().add(transfer.getAmount()));
//        fromAccount.setBalance(fromAccount.getBalance().subtract(transfer.getAmount()));

//        Transfer newTransfer = new Transfer();
//        newTransfer.setTransferId(newTransfer.getTransferId());
//        newTransfer.setAmount(transferAmount);
        /* was setting the user_id, changed it to set the account_id */
//        newTransfer.setAccountFrom(fromAccount.getId());
//        newTransfer.setAccountTo(toAccount.getId());
        transfer.setTransferStatusId(STATUS_APPROVED);
        transfer.setTransferTypeId(TYPE_SENDING);

        try {
            accountRepository.save(toAccount);
            accountRepository.save(fromAccount);
            transferRepository.save(transfer);
        } catch (Exception e) {
            //return false;
            //TODO add exception -

        }
        return transfer;
    }

    public Transfer requestTransfer (Transfer transfer) {
        transfer.setTransferTypeId(TYPE_REQUEST);
        transfer.setTransferStatusId(STATUS_PENDING);
        transferRepository.save(transfer);
        return transfer;
    }

    public List<Transfer> findAllByAccountId (long id) {
        return transferRepository.findAllByAccountId(id);
    }

    public Optional<Transfer> findByTransferId (long transferId) {
        return transferRepository.findById(transferId);
    }

    public List<Transfer> findAllByStatusAndUser (long transferStatusId, long userId) {
        long accountId = accountService.findByUserId(userId).getId();
        return transferRepository.findAllByStatusAndUser(transferStatusId, accountId);
    }

//    public void sendBucks(BigDecimal amount, long receiverId) {
//        Account receiverAccount = accountRepository.findByUserId(receiverId);
//        receiverAccount.setBalance();
//
//    }

}
