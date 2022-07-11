package com.techelevator.tenmo.business;

import com.techelevator.tenmo.dao.AccountRepository;
import com.techelevator.tenmo.dao.TransferRepository;
import com.techelevator.tenmo.exceptions.InvalidTransferAmountException;
import com.techelevator.tenmo.exceptions.NonSufficentFundsException;
import com.techelevator.tenmo.exceptions.TransferApprovalException;
import com.techelevator.tenmo.exceptions.TransferSenderReceiverException;
import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.util.BasicLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransferService {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AccountService accountService;
    @Autowired
    UserService userService;
    @Autowired
    TransferRepository transferRepository;


    public Transfer createTransfer(TransferDTO transferDetails, String principalName) {
        Transfer newTransfer = new Transfer();
        try {
             if(!accountRepository.findAll().contains(accountRepository.findByUserId(transferDetails.getFromID()))) {
                newTransfer.setTransferStatus(TransferStatus.user_not_found);
                throw new UsernameNotFoundException("This user was not found.");
            } else if (transferDetails.getAmount().compareTo(BigDecimal.valueOf(0)) <= 0) {
                newTransfer.setTransferStatus(TransferStatus.invalid_amount);
                throw new InvalidTransferAmountException("You must select a transfer amount larger then $0.00.");
            }else if(!accountRepository.findAll().contains(accountRepository.findByUserId(transferDetails.getToID()))) {
                newTransfer.setTransferStatus(TransferStatus.user_not_found);
                throw new UsernameNotFoundException("This user was not found.");
            } else if (transferDetails.getFromID() == transferDetails.getToID()) {
                newTransfer.setTransferStatus(TransferStatus.invalid_transfer);
                throw new TransferSenderReceiverException();
            } else {
                 newTransfer.setAccountIdFrom(accountRepository.findByUserId(transferDetails.getFromID()).getId());
                 newTransfer.setAccountIdTo(accountRepository.findByUserId(transferDetails.getToID()).getId());
                 newTransfer.setAmount(transferDetails.getAmount());
             }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            BasicLogger.log(e.getMessage() + " | Current user: " + principalName);
        }
        return newTransfer;
    }

    public Transfer completeTransfer (Transfer transfer) throws NonSufficentFundsException {
        try {
            Account fromAccount = accountService.findByAccountId(transfer.getAccountIdFrom());
            Account toAccount = accountService.findByAccountId(transfer.getAccountIdTo());

            BigDecimal newFromBalance =
                    accountService.subtractFromBalance(fromAccount.getId(), transfer.getAmount());
            BigDecimal newToBalance =
                    accountService.addToBalance(toAccount.getId(), transfer.getAmount());

            fromAccount = accountService.updateBalance(fromAccount, newFromBalance);
            toAccount = accountService.updateBalance(toAccount, newToBalance);

            transfer.setTransferStatus(TransferStatus.approved);

            accountRepository.saveAndFlush(fromAccount);
            accountRepository.saveAndFlush(toAccount);
        } catch (NullPointerException e) {
            BasicLogger.log(e.getMessage());
            transfer.setTransferStatus(TransferStatus.invalid_transfer);
        }
        return transfer;
    }

    public Transfer sendTransfer(Transfer transfer, String principalName) {
        if (TransferStatus.isValid(transfer.getTransferStatus())) {
            Transfer sentTransfer = transfer;
            try {
                String sendingUsername = userService.findByAccountId(transfer.getAccountIdFrom()).getUsername();
                if (!sendingUsername.equals(principalName) ) {
                    throw new TransferApprovalException();
                } else {
                    sentTransfer = completeTransfer(transfer);
                    sentTransfer.setTransferType(TransferType.send);
                    transferRepository.saveAndFlush(sentTransfer);
                }
            } catch (NonSufficentFundsException e) {
                sentTransfer.setTransferStatus(TransferStatus.nsf);
                System.out.println(e.getMessage());
                BasicLogger.log(e.getMessage() + " | Current user: " + principalName);
            } catch (TransferApprovalException ex) {
                sentTransfer.setTransferStatus(TransferStatus.unauthorized);
                System.out.println(ex.getMessage());
                BasicLogger.log(ex.getMessage() + " | Current user: " + principalName);
            } catch (Exception x) {
                sentTransfer.setTransferStatus(TransferStatus.invalid_transfer);
                BasicLogger.log(x.getMessage() + " | Current user: " + principalName);
            }
            return sentTransfer;
        } else {
            return transfer;
        }
    }

    public Transfer approveTransfer (Transfer requestedTransfer, String principalName) {
        if (requestedTransfer.getTransferStatus() == TransferStatus.pending) {
            Transfer approvedTransfer = requestedTransfer;
            try {
                String sendingUsername = userService.findByAccountId(requestedTransfer.getAccountIdFrom()).getUsername();
                if (!sendingUsername.equals(principalName) ) {
                    throw new TransferApprovalException();
                } else {
                approvedTransfer = completeTransfer(requestedTransfer);
                transferRepository.saveAndFlush(approvedTransfer);
                }
            } catch (NonSufficentFundsException e) {
                approvedTransfer.setTransferStatus(TransferStatus.nsf);
                System.out.println(e.getMessage());
                BasicLogger.log(e.getMessage() + " | Current user: " + principalName);
            } catch (TransferApprovalException ex) {
                approvedTransfer.setTransferStatus(TransferStatus.unauthorized);
                System.out.println(ex.getMessage());
                BasicLogger.log(ex.getMessage() + " | Current user: " + principalName);
            } catch (Exception x) {
                approvedTransfer.setTransferStatus(TransferStatus.invalid_transfer);
                BasicLogger.log(x.getMessage() + " | Current user: " + principalName);
            }
            return approvedTransfer;
        } else {
            return requestedTransfer;
        }
    }

    public Transfer rejectTransfer (Transfer rejectedTransfer, String principalName) {
        try {
            String sendingUsername = userService.findByAccountId(rejectedTransfer.getAccountIdFrom()).getUsername();
            if (!sendingUsername.equals(principalName) || rejectedTransfer.getTransferStatus() != TransferStatus.pending) {
                throw new TransferApprovalException();
            } else {
            rejectedTransfer.setTransferStatus(TransferStatus.rejected);
            transferRepository.saveAndFlush(rejectedTransfer);
            }
        } catch (TransferApprovalException e) {
            rejectedTransfer.setTransferStatus(TransferStatus.unauthorized);
            System.out.println(e.getMessage());
            BasicLogger.log(e.getMessage() + " | Current user: " + principalName);
        } catch (Exception x) {
            rejectedTransfer.setTransferStatus(TransferStatus.invalid_transfer);
            BasicLogger.log(x.getMessage() + " | Current user: " + principalName);
        }
        return rejectedTransfer;
    }

    public Transfer requestTransfer (Transfer transfer) {
        if (TransferStatus.isValid(transfer.getTransferStatus())) {
            transfer.setTransferType(TransferType.request);
            transfer.setTransferStatus(TransferStatus.pending);
            transferRepository.saveAndFlush(transfer);
        }
        return transfer;
    }

    public List<Transfer> findAllByAccountId (long id) {
        return transferRepository.findAllByAccountId(id);
    }

    public Transfer findById (long transferId) {
        return transferRepository.findById(transferId);
    }

//    public List<Transfer> findAllByStatusAndUser (long transferStatusId, long userId) {
//        long accountId = accountService.findByUserId(userId).getId();
//        return transferRepository.findAllByStatusAndUser(transferStatusId, accountId);
//    }

    public List<Transfer> findAllByAccountFrom(long accountFrom) {
        return transferRepository.findAllByAccountIdFrom(accountFrom);
    }

    public List<Transfer> findAllByAccountTo(long accountTo) {
        return transferRepository.findAllByAccountIdTo(accountTo);
    }

    public List<Transfer> findAllByAccountFromAndTransferStatusId(long accountId, long transferStatusId) {
        return transferRepository.findAllByAccountFromAndTransferStatusId(accountId, transferStatusId);
    }

//    public List<Transfer> findAllByAccountToAndTransferStatusId(long accountId, long transferStatusId) {
//        return transferRepository.findAllByAccountToAndTransferStatusId(accountId, transferStatusId);
//    }

}
