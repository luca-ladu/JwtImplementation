package it.luca.project.restaurant.auth;

import it.luca.project.restaurant.entity.User.User;
import it.luca.project.restaurant.repository.UserRepository;
import it.luca.project.restaurant.service.AuthorityService;
import it.luca.project.restaurant.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthorityService authorityService;

    @Autowired
    RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        User userFromDb = userRepository.findByUsername(s).orElseThrow(() -> new UsernameNotFoundException(s+" Not found"));

        ApplicationUser user = new ApplicationUser(userFromDb,authorityService,roleService);

        return user;

    }
}
