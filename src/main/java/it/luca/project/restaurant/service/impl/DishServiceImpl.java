package it.luca.project.restaurant.service.impl;

import it.luca.project.restaurant.controller.dto.DishDto;
import it.luca.project.restaurant.entity.Dish;
import it.luca.project.restaurant.exception.RequiredFieldException;
import it.luca.project.restaurant.repository.DishRepository;
import it.luca.project.restaurant.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    DishRepository dishRepository;

    @Override
    public void saveDish(DishDto dto) throws RequiredFieldException{

        Dish dish;
        try {
             dish = Dish.builder()
                    .id(null)
                    .name(dto.getName())
                    .price(dto.getPrice())
                    .description(dto.getDescription())
                    .typeOfDish(dto.getTypeOfDish())
                    .build();
             dishRepository.save(dish);
        }catch (Exception e){
            throw new RequiredFieldException(e.getMessage());
        }


    }




}
