package com.weshy.phonebook.entity;

import lombok.*;
import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@RequiredArgsConstructor
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Document("Phonebook")
public class Contact {
    @Id
    private String id;
    @NonNull
    private String firstName;
    private String lastName;
    private String middleName;
    @NonNull
    private String phoneNumber;
    @NonNull
    private String emailAddress;
}
