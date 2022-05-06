package it.luca.project.restaurant.jtw;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsernameAndPasswordAuthRequest {
    private String username;
    private String password;
}
