package com.weshy.phonebook.exceptions;

public class EmptyContactListException extends ContactNotFoundException {
    public EmptyContactListException(String message){
        super(message);
    }
}
