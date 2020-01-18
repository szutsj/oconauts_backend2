package com.octonauts.game.controller;

import com.octonauts.game.model.dto.LeaderBoardByPoints;
import com.octonauts.game.service.LeaderboardService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LeaderboardController {

    private LeaderboardService leaderboardService;

    @Autowired
    public LeaderboardController(LeaderboardService leaderboardService) {
        this.leaderboardService = leaderboardService;
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "Authorization token", required = true, dataType = "string", paramType = "header")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = LeaderBoardByPoints.class)})
    @GetMapping("/leaderboard")
    public ResponseEntity<Object> listingLeaderboard() {
        LeaderBoardByPoints leaderBoardByPoints = leaderboardService.listingLeaderBoard();
        return ResponseEntity.status(200).body(leaderBoardByPoints);
    }

}
