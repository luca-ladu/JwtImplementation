package it.luca.project.restaurant.repository;

import it.luca.project.restaurant.entity.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String us);
    boolean existsByUsername(String us);
    Optional<User> findByVerificationCode(String code);
    Optional<User> findByIdAndRecoveryUuid(Long id,String uuid);
}
