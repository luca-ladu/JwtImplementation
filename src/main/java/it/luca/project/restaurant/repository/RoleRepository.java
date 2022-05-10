package it.luca.project.restaurant.repository;

import it.luca.project.restaurant.entity.User.Role;
import it.luca.project.restaurant.entity.User.User;
import javassist.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {


    Optional<Role> findByName(String name) throws NotFoundException;
    List<Role> findByUser(User user);




}
