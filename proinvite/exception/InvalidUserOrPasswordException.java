package com.proinvite.exception;

public class InvalidUserOrPasswordException extends RuntimeException{
    public InvalidUserOrPasswordException(String message){
        super(message);
    }
}
