package com.octonauts.game.service;

import com.octonauts.game.model.dto.LeaderBoardByPoints;
import com.octonauts.game.model.dto.UserAndPoint;
import com.octonauts.game.model.entity.User;
import com.octonauts.game.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class LeaderboardService {

    private UserRepository userRepository;

    @Autowired
    public LeaderboardService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LeaderBoardByPoints listingLeaderBoard(){
        List<User> userList = userRepository.findAll();
        Collections.sort(userList);
        List<UserAndPoint> userAndPointList = new ArrayList<>();
        for (int i = userList.size() - 1; i >= 0; i--){
            UserAndPoint userAndPoint = new UserAndPoint(userList.get(i).getUsername(), userList.get(i).getPoints());
            userAndPointList.add(userAndPoint);
        }
        LeaderBoardByPoints leaderBoardByPoints = new LeaderBoardByPoints(userAndPointList);
        return leaderBoardByPoints;
    }
}
