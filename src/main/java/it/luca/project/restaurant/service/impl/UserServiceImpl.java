package it.luca.project.restaurant.service.impl;

import it.luca.project.restaurant.controller.dto.UserRegistrationDto;
import it.luca.project.restaurant.entity.User.User;
import it.luca.project.restaurant.exception.RequiredFieldException;
import it.luca.project.restaurant.exception.UnauthorizedException;
import it.luca.project.restaurant.exception.UserAlreadyExistException;
import it.luca.project.restaurant.repository.UserRepository;
import it.luca.project.restaurant.service.RoleService;
import it.luca.project.restaurant.service.UserService;
import net.bytebuddy.utility.RandomString;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleService roleService;

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public User findByIdAndUuid(Long id, String uuid) {
        return userRepository.findByIdAndRecoveryUuid(id,uuid).orElseThrow(() -> new UsernameNotFoundException("User note found"));
    }

    @Override
    public boolean checkIfUserExist(String username) throws UserAlreadyExistException {
        if(!userRepository.existsByUsername(username)) return true;
        else throw new UserAlreadyExistException(username+" already exist");
    }

    @Override
    public User buildUser(UserRegistrationDto dto) throws RequiredFieldException {

        checkUserField(dto);
        User user = new User();
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);
        user.setAccountNonLocked(true);
        user.setEmail(dto.getEmail());
        user.setVerificationCode(RandomString.make(64));
        user.setPhone(dto.getPhone());
        user.setSurname(dto.getSurname());
        user.setName(dto.getName());
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEnabled(false);
        user.setRoles(Arrays.asList(roleService.getUserBaseRole()));

        return user;

    }

    @Override
    public User verifyUserByCode(String code) throws HttpClientErrorException.Unauthorized, UnauthorizedException {

        return userRepository.findByVerificationCode(code).orElseThrow(() -> new UnauthorizedException("this code is not valid: "+code));
    }

    @Override
    public User findByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username+"Not found"));
    }

    @Override
    public void createRecoveryUuid(User user) {
        user.setRecoveryUuid(UUID.randomUUID().toString());
        userRepository.save(user);
    }

    @Override
    public void changePassword(User u,String passowrd) {

        u.setPassword(passwordEncoder.encode(passowrd));
        userRepository.save(u);
    }


    private boolean checkUserField(UserRegistrationDto dto) throws RequiredFieldException {

        if(dto.getUsername() == null || dto.getUsername().isEmpty())
            throw new RequiredFieldException("Username is mandatory");
        if(dto.getName()== null || dto.getName().isEmpty())
            throw new RequiredFieldException("Name is mandatory");
        if(dto.getSurname() == null || dto.getSurname().isEmpty())
            throw  new RequiredFieldException("Surname is mandatory");
        if(dto.getEmail() == null || dto.getEmail().isEmpty())
            throw new RequiredFieldException("Email is mandatory");
        if(dto.getPhone() == null || dto.getPhone().isEmpty())
            throw new RequiredFieldException("Phone is mandatory");
        if(dto.getPassword() == null || dto.getPassword().isEmpty())
            throw new RequiredFieldException("Password is mandatory");
        return true;
    }


}
