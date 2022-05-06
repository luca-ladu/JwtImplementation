package it.luca.project.restaurant.service.impl;

import it.luca.project.restaurant.entity.User;
import it.luca.project.restaurant.repository.UserRepository;
import it.luca.project.restaurant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }
}
