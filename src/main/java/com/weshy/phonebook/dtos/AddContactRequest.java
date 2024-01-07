package com.weshy.phonebook.dtos;

import lombok.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class AddContactRequest {
    @NonNull
    private String firstName;
    private String lastName;
    private String middleName;
    @NonNull
    private String phoneNumber;
    @NonNull
    private String emailAddress;


    @Data
    public static class ApiResponse {
        private boolean isSuccessful;
        private String message;
    }
}
