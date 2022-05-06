package it.luca.project.restaurant.entity;

import it.luca.project.restaurant.exception.RequiredFieldException;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Entity
@Builder
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Size(min = 2,message = "The name must have at least 2 charter")
    @Column(name = "NAME",nullable = false)
    private String name;
    @Column(name = "PRICE",nullable = false)
    @NonNull
    private Double price;
    @Column(name = "DESCRIPTION")
    @NonNull
    private String description;
    @NonNull
    @Column(name = "GROUPDISH",nullable = false)
    private Group typeOfDish;

}





