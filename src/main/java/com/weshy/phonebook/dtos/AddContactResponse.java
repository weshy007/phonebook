package com.weshy.phonebook.dtos;

import lombok.Data;

@Data
public class AddContactResponse {
    private String fullName;
    private String phoneNumber;
}
