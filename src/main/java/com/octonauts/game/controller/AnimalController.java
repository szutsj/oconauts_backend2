package com.octonauts.game.controller;

import com.octonauts.game.model.dto.ErrorMessage;
import com.octonauts.game.model.dto.PatientDTO;
import com.octonauts.game.model.dto.PatinentListDTO;
import com.octonauts.game.model.entity.Animal;
import com.octonauts.game.model.entity.User;
import com.octonauts.game.service.AnimalService;
import com.octonauts.game.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class AnimalController {

    private AnimalService animalService;
    private UserService userService;

    @Autowired
    public AnimalController(AnimalService animalService, UserService userService) {
        this.animalService = animalService;
        this.userService = userService;
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = PatinentListDTO.class)})
    @GetMapping("/octopod/patients/waiting")
    public ResponseEntity<Object> listOfWaitingAnimals() {
        PatinentListDTO patinentListDTO = animalService.createWaitingPatientList();
        return ResponseEntity.status(200).body(patinentListDTO);
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = PatinentListDTO.class)})
    @GetMapping("/octopod/patients/underTreatment")
    public ResponseEntity<Object> listOfAnimalsUnderTreatment() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        PatinentListDTO patinentListDTO = new PatinentListDTO();
        if (userService.findUserByName(username).isPresent()){
            User user = userService.findUserByName(username).get();
            patinentListDTO = animalService.createUnderTreatmentList(user);
        }
        return ResponseEntity.status(200).body(patinentListDTO);
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = PatientDTO.class)})
    @PostMapping("/octopod/newPatient")
    public ResponseEntity<Object> createNewPatient() {
        PatientDTO patinentDTO = animalService.createNewPatient();
        return ResponseEntity.status(200).body(patinentDTO);
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = PatientDTO.class),
            @ApiResponse(code = 404, message = "No animal with such id found!", response = ErrorMessage.class)})
    @GetMapping("/octopod/patients/{id}")
    public ResponseEntity<Object> findPatient(@PathVariable(name = "id") Long animalId) {
        if (animalService.findById(animalId).isPresent()){
            Animal animal = animalService.findById(animalId).get();
            return ResponseEntity.status(200).body(animalService.createPatientDTO(animal));
        }
        return ResponseEntity.status(404).body(new ErrorMessage("No animal with such id found!"));
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = PatientDTO.class),
            @ApiResponse(code = 410, message = "This animal is already under treatment!", response = ErrorMessage.class),
            @ApiResponse (code = 409, message = "No animal with such id found!", response = ErrorMessage.class)})
    @PutMapping("/octopod/cure/{id}")
    public ResponseEntity<Object> startTreatment(@PathVariable(name = "id") Long animalId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByName(username).get();
        if (animalService.findById(animalId).isPresent()){
            Animal animal = animalService.findById(animalId).get();
            if (animal.getTreatmentFinishedAt() != null){
                return ResponseEntity.status(409).body(new ErrorMessage("This animal is already under treatment!"));
            }
            return ResponseEntity.status(200).body(animalService.startCure(animal, user));
        }
        return ResponseEntity.status(409).body(new ErrorMessage("No animal with such id found!"));
    }

}


