package com.techelevator.tenmo.exceptions;

public class TransferApprovalException extends Exception{
    public TransferApprovalException() {
        super("You are not authorized to approve or decline this transaction.");
    }
}
