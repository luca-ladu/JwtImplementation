package it.luca.project.restaurant.exception;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data

public class RequiredFieldException extends Exception{

    public RequiredFieldException() {
    }

    public RequiredFieldException(String message) {
        super(message);
    }

    public RequiredFieldException(String message, Throwable cause) {
        super(message, cause);
    }
}
