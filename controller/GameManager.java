package controller;

import model.Mission;
import model.User;

public class GameManager {

    private final User user;
    private Mission mission;

    public GameManager(User user) {
        this.user = user;
        this.mission = null;
    }

    public boolean checkInvalidLevel(int level) {
        return level <= 0 || level > MissionManager.numberOfLevels;
    }

    public boolean checkLockedLevel(int level) {
        return level > user.getLastUnlockedLevel();
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    public void turn(int n){}

    public void well(){
        //3 time units
        // bucket = 5
    }

}
