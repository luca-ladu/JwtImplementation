package it.luca.project.restaurant.service;

import it.luca.project.restaurant.entity.Authority;
import it.luca.project.restaurant.entity.User;
import javassist.NotFoundException;

import java.util.List;

public interface AuthorityService {

    Authority findByName(String name) throws NotFoundException;
    void saveAuthority(Authority authority);
    List<Authority> findByUser(User user);
}
