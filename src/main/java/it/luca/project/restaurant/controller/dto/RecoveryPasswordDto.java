package it.luca.project.restaurant.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecoveryPasswordDto {

    @NonNull
    private String uuid;
    @NonNull
    private Long id;
    @NonNull
    private String newPassword;
}
