package it.luca.project.restaurant.auth;

import it.luca.project.restaurant.entity.Authority;
import it.luca.project.restaurant.entity.Role;
import it.luca.project.restaurant.entity.User;
import it.luca.project.restaurant.repository.RoleRepository;
import it.luca.project.restaurant.service.AuthorityService;
import it.luca.project.restaurant.service.RoleService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class ApplicationUser implements UserDetails {



    private final List<? extends GrantedAuthority> authorities;
    private final String password;
    private final String username;
    private final boolean accountNonExpired;
    private final boolean accountNonLocked;
    private final boolean credentialsNonExpired;
    private final boolean enabled;

    public ApplicationUser(User user, AuthorityService authService,RoleService roleService){
        this.authorities = retriveAuthFromRoles(user,authService,roleService);
        this.password = user.getPassword();
        this.username = user.getUsername();
        this.accountNonExpired = user.isAccountNonExpired();
        this.accountNonLocked = user.isAccountNonLocked();
        this.credentialsNonExpired = user.isCredentialsNonExpired();
        this.enabled = user.isEnabled();
    }


    private List<? extends GrantedAuthority> retriveAuthFromRoles(User user,AuthorityService authService,RoleService roleService)  {

        List<Authority> dbAuthorities = authService.findByUser(user);
        List<Role> dbRoles = roleService.findByUser(user);
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();

        dbAuthorities.forEach(
                authority -> simpleGrantedAuthorities.add(new SimpleGrantedAuthority(authority.getName())
        ));

        dbRoles.forEach( role ->
                simpleGrantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+role.getName()))
        );

       /* ruolos.stream().forEach(role -> {
            role.getAuthorities().forEach( auth -> simpleGrantedAuthorities.add(new SimpleGrantedAuthority(auth.getName())));
        });*/

        return simpleGrantedAuthorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
