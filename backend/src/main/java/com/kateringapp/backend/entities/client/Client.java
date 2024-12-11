package com.kateringapp.backend.entities.client;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Client {

    @Id
    private UUID clientId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;

}
