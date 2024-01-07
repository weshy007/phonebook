package com.weshy.phonebook.controllers;

import com.weshy.phonebook.dtos.AddContactRequest;
import com.weshy.phonebook.dtos.ApiResponse;
import com.weshy.phonebook.exceptions.ContactNotFoundException;
import com.weshy.phonebook.exceptions.EmptyContactListException;
import com.weshy.phonebook.exceptions.PhoneBookAppException;
import com.weshy.phonebook.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/contact")
@RestController
public class ContactController {

    @Autowired
    private ContactService contactService;
    @PostMapping("/addContact")
    public ResponseEntity<?> addNewContact(@RequestBody AddContactRequest request){
        try{
            return new ResponseEntity<>(contactService.saveContact(request), HttpStatus.CREATED);
        }catch (PhoneBookAppException error) {
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/mobile/{phoneNumber}")
    public ResponseEntity<?> getContactByPhoneNumber(@PathVariable String phoneNumber) {
        try{
            return new ResponseEntity<>(contactService.findContactByPhoneNumber(phoneNumber), HttpStatus.FOUND);
        } catch (ContactNotFoundException error) {
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/email{emailAddress}")
    public ResponseEntity<?> getContactByEmailAddress(@PathVariable String emailAddress){
        try{
            return new ResponseEntity<>(contactService.findContactByEmailAddress(emailAddress), HttpStatus.FOUND);
        }catch (ContactNotFoundException error){
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<?> getContactByName(@PathVariable String name){
        try{
            return new ResponseEntity<>(contactService.findContactByFirstNameOrLastNameOrMiddleName(name), HttpStatus.FOUND);
        }catch(ContactNotFoundException error){
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAllContact(){
        try{
            return new ResponseEntity<>(contactService.findAllContact(), HttpStatus.FOUND);
        }catch (EmptyContactListException error){
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{phoneNumber}")
    public ResponseEntity<?> deleteByPhoneNumber(@PathVariable String phoneNumber){
        try{
            contactService.deleteContactByMobileNumber(phoneNumber);
            return new ResponseEntity<>(new ApiResponse(true, "Deleted Successfully!"), HttpStatus.FOUND);
        }catch (ContactNotFoundException error){
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/del/{emailAddress}")
    public ResponseEntity<?> deleteByEmailAddress(@PathVariable String emailAddress){
        try{
            contactService.deleteContactByEmailAddress(emailAddress);
            return new ResponseEntity<>(new ApiResponse(true, "Deleted Successfully!"), HttpStatus.FOUND);
        }catch (ContactNotFoundException error){
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteAll")
    public void deleteAll(){
        contactService.deleteAllContact();
    }

}
