package com.weshy.phonebook.repository;

import com.weshy.phonebook.entity.Contact;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
@Repository
public interface ContactRepository extends MongoRepository<Contact, String> {
    Contact findContactByEmailAddress(String emailAddress);

    List<Contact> findContactByFirstName(String firstName);
    List<Contact> findContactByLastName(String lastName);
    List<Contact> findContactByMiddleName(String middleName);

    Contact findContactByPhoneNumber(String phoneNumber);

}
