package it.luca.project.restaurant.service;

import it.luca.project.restaurant.controller.dto.UserRegistrationDto;
import it.luca.project.restaurant.entity.User.User;
import it.luca.project.restaurant.exception.RequiredFieldException;
import it.luca.project.restaurant.exception.UnauthorizedException;
import it.luca.project.restaurant.exception.UserAlreadyExistException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {

    void saveUser(User user);
    void deleteUser(User user);
    User findByIdAndUuid(Long id,String uuid);
    boolean checkIfUserExist(String username) throws UserAlreadyExistException;
    User buildUser(UserRegistrationDto dto) throws RequiredFieldException;
    User verifyUserByCode(String code) throws  UnauthorizedException;
    User findByUsername(String username) throws UsernameNotFoundException;
    void createRecoveryUuid(User user);
    void changePassword(User u,String password);

}
