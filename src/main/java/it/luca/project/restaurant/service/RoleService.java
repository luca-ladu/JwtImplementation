package it.luca.project.restaurant.service;

import it.luca.project.restaurant.entity.Role;
import it.luca.project.restaurant.entity.User;
import javassist.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RoleService {

    Role findByName(String name) throws NotFoundException;
    List<Role> findByUser(User user) ;
    void saveRole(Role role);
}
