package it.luca.project.restaurant.service;

import it.luca.project.restaurant.entity.User.Role;
import it.luca.project.restaurant.entity.User.User;
import javassist.NotFoundException;

import java.util.List;

public interface RoleService {

    Role findByName(String name) throws NotFoundException;
    List<Role> findByUser(User user) ;
    void saveRole(Role role);
    Role getUserBaseRole();
}
