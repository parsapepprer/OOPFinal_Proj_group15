package controller;

import model.Game;
import model.Mission;
import model.User;

public class GameManager {

    private final User user;
    private Game game;

    public GameManager(User user) {
        this.user = user;
    }

    public boolean checkInvalidLevel(int level) {
        return level <= 0 || level > MissionManager.getInstance().getNumberOfLevels();
    }

    public boolean checkLockedLevel(int level) {
        return level > user.getLastUnlockedLevel();
    }

    public void setGame(int level) {
        this.game = new Game(MissionManager.getInstance().getMission(level), user);
    }

    public void turn(int n) {
    }

    public void well() {
        //3 time units
        // bucket = 5
    }

}
