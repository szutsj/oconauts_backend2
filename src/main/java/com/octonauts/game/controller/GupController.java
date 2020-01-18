package com.octonauts.game.controller;

import com.octonauts.game.model.dto.*;
import com.octonauts.game.model.entity.CrewMember;
import com.octonauts.game.model.entity.Gup;
import com.octonauts.game.model.entity.Octopod;
import com.octonauts.game.model.entity.User;
import com.octonauts.game.repository.OctopodRepository;
import com.octonauts.game.service.GupService;
import com.octonauts.game.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GupController {

    private GupService gupService;
    private UserService userService;
    private OctopodRepository octopodRepository;

    @Autowired
    public GupController(GupService gupService, UserService userService, OctopodRepository octopodRepository) {
        this.gupService = gupService;
        this.userService = userService;
        this.octopodRepository = octopodRepository;
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = GupListDTO.class)})
    @GetMapping("/octopod/gups")
    public ResponseEntity<Object> gupList() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByName(username).get();
        Octopod octopod = octopodRepository.findByUser(user).get();
        GupListDTO gupListDTO = gupService.createGupList(octopod);
        return ResponseEntity.status(200).body(gupListDTO);
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = GupDTO.class),
            @ApiResponse(code = 410, message = "This gup is already active!", response = ErrorMessage.class),
            @ApiResponse(code = 408, message = "Not enough points!", response = ErrorMessage.class),
            @ApiResponse (code = 409, message = "No gup with such id found in your octopod!", response = ErrorMessage.class)})
    @PutMapping("/octopod/crew/{id}")
    public ResponseEntity<Object> activateGup(@PathVariable(name = "id") Long gupId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByName(username).get();
        Octopod octopod = octopodRepository.findByUser(user).get();
        if (gupService.findById(gupId).isPresent()){
            Gup gup  = gupService.findById(gupId).get();
            if (gup.isActive()){
                return ResponseEntity.status(410).body(new ErrorMessage("This gup is already active!"));
            }
            user = userService.recalculatePoints(user);
            if (user.getPoints() < gup.getPointsForActivate()){
                return ResponseEntity.status(408).body(new ErrorMessage("Not enough points!"));
            }
            return ResponseEntity.status(200).body(gupService.activate(gup));
        }
        return ResponseEntity.status(409).body(new ErrorMessage("No gup with such id found in your octopod!"));
    }

}
