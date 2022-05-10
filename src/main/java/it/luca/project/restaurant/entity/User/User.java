package it.luca.project.restaurant.entity.User;

import it.luca.project.restaurant.entity.Reservation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    private String password;
    private String username;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private String verificationCode;
    private String recoveryUuid;

    @OneToMany(mappedBy = "user")
    private List<Role> roles;

    @OneToMany(mappedBy = "user")
    private List<Reservation> reservations;


    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                '}';
    }
}
