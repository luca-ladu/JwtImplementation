package it.luca.project.restaurant.controller.dto;

import it.luca.project.restaurant.entity.Group;
import lombok.*;

import javax.persistence.Column;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishDto {

    private String name;
    private Double price;
    private String description;
    private Group typeOfDish;
}
