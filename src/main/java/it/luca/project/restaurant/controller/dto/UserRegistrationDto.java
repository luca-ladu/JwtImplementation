package it.luca.project.restaurant.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDto {

    @NonNull
    private String password;
    @NonNull
    private String username;
    @NonNull
    private String name;
    @NonNull
    private String surname;
    private String email;
    @NonNull
    private String phone;

}
