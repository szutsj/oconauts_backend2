package com.octonauts.game.service;

import com.octonauts.game.model.entity.User;
import com.octonauts.game.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private OctopodService octopodService;

    @Autowired
    public UserService(UserRepository userRepository, OctopodService octopodService) {
        this.userRepository = userRepository;
        this.octopodService = octopodService;
    }

    public boolean checkUserByName(String usernameToCheck) {
        return userRepository.findUserByUsername(usernameToCheck).isPresent();
    }

    public boolean checkUserByNameAndPassword(String usernameToCheck, String passwordToCheck) {
        return userRepository.findUserByUsernameAndPassword(usernameToCheck, passwordToCheck).isPresent();
    }

    public Optional<User> findUserByName(String username) {
        return userRepository.findUserByUsername(username);
    }

    public User saveUser(String username, String password) {
        if (!checkUserByName(username)) {
            User newUser = new User(username, password);
            newUser.setOctopod(octopodService.initOctopod(newUser));
            userRepository.save(newUser);
            newUser.getOctopod().setUser(newUser);
            octopodService.save(newUser.getOctopod());
            return userRepository.save(newUser);
        }
        return null;
    }

    public User recalculatePoints(User user){
        int actualPoints = octopodService.recalculatePoints(user);
        user.setPoints(actualPoints);
        return userRepository.save(user);
    }

}