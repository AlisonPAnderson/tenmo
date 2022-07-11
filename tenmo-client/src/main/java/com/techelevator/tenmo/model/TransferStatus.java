package com.techelevator.tenmo.model;

public enum TransferStatus {
    pending,
    approved,
    rejected,
    invalid_transfer,
    invalid_amount,
    user_not_found,
    nsf,
    unauthorized;

    // Transfer transfer;
/*
        private long transferStatusId;

        private String transferStatusDescription;*/

    TransferStatus() {}



/*    TransferStatus(long transferStatusId, String transferStatusDescription) {
        this.transferStatusId = transferStatusId;
        this.transferStatusDescription = transferStatusDescription;
    }

//    public TransferStatus(long transferStatusId) {
//        this.transferStatusId = transferStatusId;
//    }

    public String getTransferStatusDescription() {
        return transferStatusDescription;
    }

    public void setTransferStatusDescription(String transferStatusDescription) {
        this.transferStatusDescription = transferStatusDescription;
    }

    public long getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(long transferStatusId) {
        this.transferStatusId = transferStatusId;
    }*/


}
