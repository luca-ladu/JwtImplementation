package it.luca.project.restaurant.repository;

import it.luca.project.restaurant.auth.ApplicationUser;
import it.luca.project.restaurant.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String us);
}
