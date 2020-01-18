package com.octonauts.game.model.dto;

public class UserAndPoint {
    private String username;
    private int points;

    public UserAndPoint(String username, int points) {
        this.username = username;
        this.points = points;
    }

    public UserAndPoint() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
