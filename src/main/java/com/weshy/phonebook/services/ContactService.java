package com.weshy.phonebook.services;

import com.weshy.phonebook.dtos.AddContactRequest;
import com.weshy.phonebook.dtos.AddContactResponse;
import com.weshy.phonebook.entity.Contact;

import java.util.List;

public interface ContactService {
    AddContactResponse saveContact(AddContactRequest newContactRequest);

    List<Contact> findContactByFirstNameOrLastNameOrMiddleName(String name);

    Contact findContactByPhoneNumber(String phoneNumber);
    Contact findContactByEmailAddress(String emailAddress);

    List<Contact> findAllContact();

    void deleteContactByEmailAddress(String emailAddress);
    void deleteContactByMobileNumber(String phoneNumber);
    void deleteAllContact();

}
