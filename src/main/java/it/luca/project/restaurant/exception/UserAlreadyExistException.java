package it.luca.project.restaurant.exception;

import lombok.Data;

@Data
public class UserAlreadyExistException extends Exception{

    public UserAlreadyExistException(String message) {
        super(message);
    }
}
