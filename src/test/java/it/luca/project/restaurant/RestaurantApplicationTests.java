package it.luca.project.restaurant;

import it.luca.project.restaurant.entity.Authority;
import it.luca.project.restaurant.entity.Role;
import it.luca.project.restaurant.entity.User;
import it.luca.project.restaurant.service.AuthorityService;
import it.luca.project.restaurant.service.RoleService;
import it.luca.project.restaurant.service.UserService;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

@SpringBootTest
class RestaurantApplicationTests {

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserService userService;

	@Autowired
	RoleService roleService;

	@Autowired
	AuthorityService authorityService;


	@Test
	void contextLoads() {


		User useradmin = new User();
		useradmin.setUsername("luca");
		useradmin.setPassword(passwordEncoder.encode("admin"));
		useradmin.setEnabled(true);
		useradmin.setCredentialsNonExpired(true);
		useradmin.setAccountNonLocked(true);
		useradmin.setAccountNonExpired(true);


		userService.saveUser(useradmin);






		Authority canRead = new Authority();
		Authority canWrite = new Authority();

		canRead.setName("can:read");
		canWrite.setName("can:write");



		authorityService.saveAuthority(canRead);
		authorityService.saveAuthority(canWrite);


		Role roleadmin = new Role();
		roleadmin.setName("ADMIN");
		roleadmin.setUser(useradmin);
		roleadmin.setAuthorities(Arrays.asList(canRead,canWrite));

		roleService.saveRole(roleadmin);


	}

}
