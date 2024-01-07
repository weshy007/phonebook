package com.weshy.phonebook.services;

import com.weshy.phonebook.dtos.AddContactRequest;
import com.weshy.phonebook.dtos.AddContactResponse;
import com.weshy.phonebook.entity.Contact;
import com.weshy.phonebook.exceptions.ContactNotFoundException;
import com.weshy.phonebook.exceptions.EmptyContactListException;
import com.weshy.phonebook.exceptions.PhoneBookAppException;
import com.weshy.phonebook.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactServiceImp implements ContactService{
    @Autowired
    private ContactRepository database;

    @Override
    public AddContactResponse saveContact(AddContactRequest newContactRequest) {
        if (phoneNumberExist(newContactRequest.getPhoneNumber()))
            throw new PhoneBookAppException("Phone Number already exists!");
        if (emailAddressExists(newContactRequest.getEmailAddress()))
            throw new PhoneBookAppException("Email Address already exists!");

        String firstName = newContactRequest.getFirstName();
        String lastName = newContactRequest.getLastName();
        String middleName = newContactRequest.getMiddleName();
        String email = newContactRequest.getEmailAddress();
        String mobile = newContactRequest.getPhoneNumber();

        Contact newContact = new Contact(firstName, mobile, email);
        newContact.setLastName(lastName);
        newContact.setMiddleName(middleName);
        database.save(newContact);
        AddContactResponse contactResponse = new AddContactResponse();

        if(lastName == null && middleName == null){
            contactResponse.setFullName(firstName);
        } else if (middleName == null) {
            contactResponse.setFullName(newContactRequest.getFirstName() +" "+ newContactRequest.getLastName());
        } else if (lastName == null) {
            contactResponse.setFullName(firstName + " " + middleName);
        }else {
            contactResponse.setFullName(firstName+ " " + middleName + " " + lastName);
        }
        contactResponse.setPhoneNumber(newContactRequest.getPhoneNumber());

        return contactResponse;
    }

    private boolean phoneNumberExist(String phoneNumber){
        return database.findContactByPhoneNumber(phoneNumber) != null;
    }

    private boolean emailAddressExists(String emailAddress){
        return database.findContactByEmailAddress(emailAddress) != null;
    }

    @Override
    public List<Contact> findContactByFirstNameOrLastNameOrMiddleName(String name){
        if (nameDoesNotExist(name)) throw new ContactNotFoundException(name + " does not exist!");
        List<Contact> foundContacts = new ArrayList<>();
        foundContacts.addAll(database.findContactByFirstName(name));
        foundContacts.addAll(database.findContactByMiddleName(name));
        foundContacts.addAll(database.findContactByLastName(name));

        return foundContacts;
    }

    private boolean nameDoesNotExist(String name){
        List<Contact> foundContacts = new ArrayList<>();

        foundContacts.addAll(database.findContactByFirstName(name));
        foundContacts.addAll(database.findContactByMiddleName(name));
        foundContacts.addAll(database.findContactByLastName(name));

        return foundContacts.isEmpty();
    }

    @Override
    public Contact findContactByPhoneNumber(String phoneNumber) {
        if (phoneNumberDoesNotExist(phoneNumber))
            throw new ContactNotFoundException(phoneNumber + " does not exist!");
        return database.findContactByPhoneNumber(phoneNumber);
    }

    private boolean phoneNumberDoesNotExist(String phoneNumber){
        return database.findContactByPhoneNumber(phoneNumber) == null;
    }

    @Override
    public Contact findContactByEmailAddress(String emailAddress) {
        if (emailAddressDoesNotExist(emailAddress.toLowerCase()))
            throw new ContactNotFoundException(emailAddress + "does not exist");
        return database.findContactByEmailAddress(emailAddress.toLowerCase());
    }

    private boolean emailAddressDoesNotExist(String emailAddress){
        return database.findContactByEmailAddress(emailAddress) == null;
    }

    @Override
    public List<Contact> findAllContact(){
        if (database.findAll().isEmpty()) throw new EmptyContactListException("Empty Contact");
        return database.findAll();
    }

    @Override
    public void deleteContactByEmailAddress(String emailAddress){
        if( emailAddressDoesNotExist(emailAddress.toLowerCase()))
            throw new ContactNotFoundException(emailAddress + " does not exist");
        Contact contact = database.findContactByEmailAddress(emailAddress.toLowerCase());
        database.delete(contact);
    }

    @Override
    public void deleteContactByMobileNumber(String phoneNumber){
        if (phoneNumberDoesNotExist(phoneNumber))
            throw new ContactNotFoundException(phoneNumber + "does not exist!");
        database.delete(database.findContactByPhoneNumber(phoneNumber));
    }

    @Override
    public void deleteAllContact(){
        database.deleteAll();
    }

    @Override
    public String toString() {
        return "ContactServiceImpl{" +
                "database=" + database +
                '}';
    }

}
