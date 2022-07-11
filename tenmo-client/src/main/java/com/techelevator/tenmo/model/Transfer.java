package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {

    private long transferId;

/*    private long transferTypeId;
    private long transferStatusId;*/

    private long accountIdFrom;
    private long accountIdTo;
    private BigDecimal amount;

    private TransferStatus transferStatus;
    private TransferType transferType;

    private Account fromAccount;
    private Account toAccount;

    //<editor-fold desc="Getters & Setters">
    public long getTransferId() {
        return transferId;
    }

    public void setTransferId(long transferId) {
        this.transferId = transferId;
    }

/*    public long getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(long transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public long getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(long transferStatusId) {
        this.transferStatusId = transferStatusId;
    }*/

    public long getAccountIdFrom() {
        return accountIdFrom;
    }

    public void setAccountIdFrom(long accountIdFrom) {
        this.accountIdFrom = accountIdFrom;
    }

    public long getAccountIdTo() {
        return accountIdTo;
    }

    public void setAccountIdTo(long accountIdTo) {
        this.accountIdTo = accountIdTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransferStatus getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(TransferStatus transferStatus) {
        this.transferStatus = transferStatus;
    }

    public TransferType getTransferType() {
        return transferType;
    }

    public void setTransferType(TransferType transferType) {
        this.transferType = transferType;
    }

    public Account getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(Account fromAccount) {
        this.fromAccount = fromAccount;
    }

    public Account getToAccount() {
        return toAccount;
    }

    public void setToAccount(Account toAccount) {
        this.toAccount = toAccount;
    }

    //</editor-fold>

    public Transfer() {}

//    public Transfer(long transferId, long transferTypeId, long transferStatusId, long accountFrom, long accountTo, BigDecimal amount, Account fromAccount, Account toAccount) {
//        this.transferId = transferId;
//        this.transferTypeId = transferTypeId;
//        this.transferStatusId = transferStatusId;
//        this.accountFrom = accountFrom;
//        this.accountTo = accountTo;
//        this.amount = amount;
//        this.fromAccount = fromAccount;
//        this.toAccount = toAccount;
//    }

/*        public Transfer(long transferId, long transferTypeId, long transferStatusId, long accountIdFrom, long accountIdTo, BigDecimal amount, TransferStatus transferStatus, TransferType transferType, Account fromAccount, Account toAccount) {
        this.transferId = transferId;
        this.transferTypeId = transferTypeId;
        this.transferStatusId = transferStatusId;
        this.accountIdFrom = accountIdFrom;
        this.accountIdTo = accountIdTo;
        this.amount = amount;
        this.transferStatus = transferStatus;
        this.transferType = transferType;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
    }*/

    public Transfer(long transferId, long accountIdFrom, long accountIdTo, BigDecimal amount, TransferStatus transferStatus, TransferType transferType, Account fromAccount, Account toAccount) {
        this.transferId = transferId;
        this.accountIdFrom = accountIdFrom;
        this.accountIdTo = accountIdTo;
        this.amount = amount;
        this.transferStatus = transferStatus;
        this.transferType = transferType;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
    }
}
