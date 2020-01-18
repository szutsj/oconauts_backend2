package com.octonauts.game.controller;

import com.octonauts.game.model.dto.*;
import com.octonauts.game.model.entity.User;
import com.octonauts.game.security.JWT.JWTUtility;
import com.octonauts.game.service.OctopodService;
import com.octonauts.game.service.UserService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private UserService userService;
    private OctopodService octopodService;

    @Autowired
    public UserController(UserService userService, OctopodService octopodService) {
        this.userService = userService;
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = RegisterResponseDTO.class),
            @ApiResponse(code = 400, message = "Username and password are required.", response = ErrorMessage.class),
            @ApiResponse(code = 400, message = "Username is required.", response = ErrorMessage.class),
            @ApiResponse(code = 400, message = "Password is required.", response = ErrorMessage.class),
            @ApiResponse(code = 409, message = "Username is already taken", response = ErrorMessage.class)})
    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterRequestDTO registerRequestDTO) {
        String username = registerRequestDTO.getUsername();
        String password = registerRequestDTO.getPassword();

        if ((username == null || username.isEmpty()) && (password == null || password.isEmpty())) {
            return ResponseEntity.status(400).body(new ErrorMessage("Username and password are required."));
        }

        if (username == null || username.isEmpty()) {
            return ResponseEntity.status(400).body(new ErrorMessage("Username is required."));
        }

        if (password == null || password.isEmpty()) {
            return ResponseEntity.status(400).body(new ErrorMessage("Password is required."));
        }

        if (userService.checkUserByName(username)) {
            return ResponseEntity.status(409).body(new ErrorMessage(username + " as username is already taken."));
        }

        User newUser = userService.saveUser(username, password);

        return ResponseEntity.status(200).body(new RegisterResponseDTO(newUser.getId(), newUser.getUsername(),
                newUser.getOctopod().getId()));
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = TokenMessage.class),
            @ApiResponse(code = 400, message = "Username and password are required.", response = ErrorMessage.class),
            @ApiResponse(code = 400, message = "Username is required.", response = ErrorMessage.class),
            @ApiResponse(code = 400, message = "Password is required.", response = ErrorMessage.class),
            @ApiResponse(code = 401, message = "Username or password is incorrect.", response = ErrorMessage.class)})
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        String username = loginRequestDTO.getUsername();
        String password = loginRequestDTO.getPassword();

        if ((username == null || username.isEmpty()) && (password == null || password.isEmpty())) {
            return ResponseEntity.status(400).body(new ErrorMessage("Username and password are required."));
        }

        if (username == null || username.isEmpty()) {
            return ResponseEntity.status(400).body(new ErrorMessage("Username is required."));
        }

        if (password == null || password.isEmpty()) {
            return ResponseEntity.status(400).body(new ErrorMessage("Password is required."));
        }

        if (!userService.checkUserByNameAndPassword(username, password)) {
            return ResponseEntity.status(401).body(new ErrorMessage("Username or password is incorrect."));
        }

        return ResponseEntity.status(200).body(new TokenMessage(JWTUtility.generateToken(username)));
    }

}
