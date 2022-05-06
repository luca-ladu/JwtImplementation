package it.luca.project.restaurant.service.impl;

import it.luca.project.restaurant.entity.Authority;
import it.luca.project.restaurant.entity.User;
import it.luca.project.restaurant.repository.AuthoritiesRepository;
import it.luca.project.restaurant.service.AuthorityService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthortyServiceImpl implements AuthorityService {

    @Autowired
    private AuthoritiesRepository authoritiesRepository;

    @Override
    public Authority findByName(String name) throws NotFoundException {
        return authoritiesRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException(name+" not found"));
    }

    @Override
    public void saveAuthority(Authority authority) {
        authoritiesRepository.save(authority);
    }

    @Override
    public List<Authority> findByUser(User user) {

        return authoritiesRepository.findByRoleAndUser(user.getId());
    }
}
