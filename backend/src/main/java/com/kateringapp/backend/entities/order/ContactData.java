package com.kateringapp.backend.entities.order;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ContactData {

    @NotNull
    private String name;
    @NotNull
    private String surname;
    @NotNull
    private String address;
    private LocalDateTime orderDateTime;
    private LocalDateTime dueDateTime;
}
