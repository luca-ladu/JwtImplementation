package it.luca.project.restaurant.repository;

import it.luca.project.restaurant.entity.Dish;
import it.luca.project.restaurant.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DishRepository extends JpaRepository<Dish,Long> {

    Optional<Dish> findByName(String name);
    List<Dish> findByTypeOfDish(Group group);

}
