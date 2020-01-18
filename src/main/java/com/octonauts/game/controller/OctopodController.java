package com.octonauts.game.controller;

import com.octonauts.game.model.dto.ErrorMessage;
import com.octonauts.game.model.dto.PatientDTO;
import com.octonauts.game.model.dto.UserAndPoint;
import com.octonauts.game.model.entity.Animal;
import com.octonauts.game.model.entity.User;
import com.octonauts.game.service.OctopodService;
import com.octonauts.game.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OctopodController {

    private OctopodService octopodService;
    private UserService userService;

    @Autowired
    public OctopodController(OctopodService octopodService, UserService userService) {
        this.octopodService = octopodService;
        this.userService = userService;
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = UserAndPoint.class)})
    @PutMapping("/octopod/updatePoints")
    public ResponseEntity<Object> updatePoints() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByName(username).get();
        UserAndPoint userAndPoint = octopodService.udatePoints(user);
        return ResponseEntity.status(200).body(userAndPoint);
    }

}
