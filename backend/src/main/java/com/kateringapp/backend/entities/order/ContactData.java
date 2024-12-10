package com.kateringapp.backend.entities.order;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ContactData {

    @NotNull
    private String name;
    @NotNull
    private String surname;
    private LocalDateTime orderDateTime;
    private LocalDateTime dueDateTime;
}
