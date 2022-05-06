package it.luca.project.restaurant.repository;

import it.luca.project.restaurant.entity.Role;
import it.luca.project.restaurant.entity.User;
import javassist.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role,Long> {


    Optional<Role> findByName(String name) throws NotFoundException;
    List<Role> findByUser(User user);




}
