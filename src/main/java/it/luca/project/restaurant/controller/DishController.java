package it.luca.project.restaurant.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.luca.project.restaurant.controller.dto.DishDto;
import it.luca.project.restaurant.controller.dto.GenericResponse;
import it.luca.project.restaurant.exception.RequiredFieldException;
import it.luca.project.restaurant.service.DishService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.HttpURLConnection;

@RestController
@RequestMapping(path = "v1/api")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class DishController {

    private static final Logger log = LoggerFactory.getLogger(DishController.class);

    @Autowired
    private DishService dishService;

    @PreAuthorize("hasAuthority('can:write')")
    @ApiOperation(value = "createNewDish",notes = "This Api recive new dish and save it")
    @PostMapping(path = "/insert/new/dish",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(
            value = {
                 @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Dish created succesfully"),
                 @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "You're not authorized"),
                 @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server errror")
            }
    )
    public ResponseEntity<GenericResponse<?>> createNewDish(@RequestBody(required = true) DishDto dish) throws RequiredFieldException {

        GenericResponse<String> respBody = new GenericResponse<>();

        log.info("Creating new Dish");

        dishService.saveDish(dish);

        respBody.setMessage("Dish correctly created");
        respBody.setObject(null);

        return  ResponseEntity.status(HttpStatus.OK).body(respBody);


    }


}
