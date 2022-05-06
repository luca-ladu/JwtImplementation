package it.luca.project.restaurant.repository;

import it.luca.project.restaurant.entity.Authority;
import org.checkerframework.checker.nullness.Opt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface AuthoritiesRepository extends JpaRepository<Authority,Long> {

    Optional<Authority> findByName(String name);


    /**
     * SELECT * FROM restaurant.authority inner join ( role inner join role_authorities on role.id=role_authorities.role_id)
     * on restaurant.authority.id=role_authorities.authority_id where user_id=0;**/

    @Query("SELECT A FROM Authority A INNER JOIN A.roles role where role.user.id in :userId")
    List<Authority> findByRoleAndUser(@Param("userId") Long userId);

}
