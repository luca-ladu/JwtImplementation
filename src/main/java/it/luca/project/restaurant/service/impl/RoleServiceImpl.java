package it.luca.project.restaurant.service.impl;

import it.luca.project.restaurant.entity.Role;
import it.luca.project.restaurant.entity.User;
import it.luca.project.restaurant.repository.RoleRepository;
import it.luca.project.restaurant.service.RoleService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;


    @Override
    public Role findByName(String name) throws NotFoundException {
        return roleRepository.findByName(name).orElseThrow(() -> new NotFoundException(name+" role not found"));
    }

    @Override
    public List<Role> findByUser(User user) {
        List<Role> roles = roleRepository.findByUser(user);
        return roles;
    }

    @Override
    public void saveRole(Role role) {

        roleRepository.save(role);
    }
}
