package it.luca.project.restaurant.entity.User;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Authority implements Comparable<Authority>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String name;

    @ManyToMany(mappedBy = "authorities")
    private List<Role> roles;

    @Override
    public int compareTo(Authority o) {
        return this.getName().compareTo(o.getName());
    }
}
