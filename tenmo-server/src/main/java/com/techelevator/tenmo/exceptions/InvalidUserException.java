package com.techelevator.tenmo.exceptions;

public class InvalidUserException extends Exception{
    public InvalidUserException() {
    super("You entered an invalid user ID.");
    }
}
