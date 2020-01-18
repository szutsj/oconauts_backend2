package com.octonauts.game.model.dto;

import java.util.List;

public class LeaderBoardByPoints {

    private List<UserAndPoint> leaderboard;

    public LeaderBoardByPoints(List<UserAndPoint> leaderboard) {
        this.leaderboard = leaderboard;
    }

    public LeaderBoardByPoints() {
    }

    public List<UserAndPoint> getLeaderboard() {
        return leaderboard;
    }

    public void setLeaderboard(List<UserAndPoint> leaderboard) {
        this.leaderboard = leaderboard;
    }
}
