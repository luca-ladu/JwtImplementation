package it.luca.project.restaurant.entity.User;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Role implements Comparable<Role>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String name;

    @ManyToMany()
    @JoinTable(
            name = "role_authorities",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id"))
    private List<Authority> authorities;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public int compareTo(Role o) {
        return this.getName().compareTo(o.getName());
    }
}
