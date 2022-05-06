package it.luca.project.restaurant.service;

import it.luca.project.restaurant.controller.dto.DishDto;
import it.luca.project.restaurant.exception.RequiredFieldException;

public interface DishService {

    void saveDish(DishDto dto) throws RequiredFieldException;
}
