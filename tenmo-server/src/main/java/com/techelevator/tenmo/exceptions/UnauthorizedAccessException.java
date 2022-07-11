package com.techelevator.tenmo.exceptions;

public class UnauthorizedAccessException extends Exception {
    public UnauthorizedAccessException() {super("You are not authorized to access this information."); }
}
