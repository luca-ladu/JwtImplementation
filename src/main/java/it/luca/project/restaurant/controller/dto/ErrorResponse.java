package it.luca.project.restaurant.controller.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ErrorResponse {
    private HttpStatus status;
    private LocalDateTime timeSt;
    private String message;

}
