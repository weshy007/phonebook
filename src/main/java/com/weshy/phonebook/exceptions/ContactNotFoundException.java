package com.weshy.phonebook.exceptions;

public class ContactNotFoundException extends PhoneBookAppException{
    public ContactNotFoundException(String message){
        super(message);
    }
}
