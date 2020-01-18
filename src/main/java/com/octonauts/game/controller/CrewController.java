package com.octonauts.game.controller;

import com.octonauts.game.model.dto.*;
import com.octonauts.game.model.entity.Animal;
import com.octonauts.game.model.entity.CrewMember;
import com.octonauts.game.model.entity.Octopod;
import com.octonauts.game.model.entity.User;
import com.octonauts.game.repository.OctopodRepository;
import com.octonauts.game.service.CrewService;
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
public class CrewController {

    private CrewService crewService;
    private UserService userService;
    private OctopodRepository octopodRepository;

    @Autowired
    public CrewController(CrewService crewService, UserService userService, OctopodRepository octopodRepository) {
        this.crewService = crewService;
        this.userService = userService;
        this.octopodRepository = octopodRepository;
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = CrewDTO.class)})
    @GetMapping("/octopod/crew")
    public ResponseEntity<Object> crewList() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByName(username).get();
        Octopod octopod = octopodRepository.findByUser(user).get();
        CrewDTO crewDTO = crewService.createCrewList(octopod);
        return ResponseEntity.status(200).body(crewDTO);
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = CrewDTO.class),
            @ApiResponse(code = 410, message = "This crew member is already active!", response = ErrorMessage.class),
            @ApiResponse(code = 408, message = "Not enough points!", response = ErrorMessage.class),
            @ApiResponse (code = 409, message = "No crew member with such id found in your octopod!", response = ErrorMessage.class)})
    @PutMapping("/octopod/crew/{id}")
    public ResponseEntity<Object> activateCrewMember(@PathVariable(name = "id") Long crewMemberId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByName(username).get();
        Octopod octopod = octopodRepository.findByUser(user).get();
        if (crewService.findById(crewMemberId).isPresent()){
            CrewMember crewMember = crewService.findById(crewMemberId).get();
            if (crewMember.isActive()){
                return ResponseEntity.status(410).body(new ErrorMessage("This crew member is already active!"));
            }
            user = userService.recalculatePoints(user);
            if (user.getPoints() < crewMember.getPointsForActivate()){
                return ResponseEntity.status(408).body(new ErrorMessage("Not enough points!"));
            }
            return ResponseEntity.status(200).body(crewService.activate(crewMember));
        }
        return ResponseEntity.status(409).body(new ErrorMessage("No crew member with such id found in your octopod!"));
    }


}
