package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.*;

import java.util.HashMap;

public class MissionManager {

    public static int numberOfLevels = 0;
    private HashMap<Integer, Mission> missions;

    public MissionManager() {
        load();
    }

    private void processMission(int level, Mission mission) {
        if (level <= missions.size() && level >= 0) {
            missions.replace(level, mission);
            Logger.log("admin", "The missions of level " + level + " was changed!");
            save();
        } else if (level == missions.size() + 1) {
            missions.put(level, mission);
            numberOfLevels++;
            Logger.log("admin", "a new level (" + level + ") was added to levels!");
            save();
        }
    }

    public Mission getMission(int level) {
        return missions.get(level);
    }

    private void load() {
        String missionsText = FileManager.read("missions.json");
        if (!missionsText.isEmpty()) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            missions = gson.fromJson(missionsText, new TypeToken<HashMap<Integer, Mission>>() {
            }.getType());
        } else {
            missions = new HashMap<>();
        }
        numberOfLevels = missions.size();
    }

    private void save() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String missionsText = gson.toJson(missions);
        FileManager.write("missions.json", missionsText);
    }

    public static void main(String[] args) {

        MissionManager missionManager = new MissionManager();

        int level;
        int numberOfInitialCoins;
        HashMap<String, Integer> wildAnimalsTime = new HashMap<>();
        HashMap<String, Integer> tasks = new HashMap<>();
        int maxPrizeTime;
        int prize;

        // ---------- Enter the Specifications -----------
        level = 3;
        numberOfInitialCoins = 100;
        wildAnimalsTime.put("Lion1", 10);
        wildAnimalsTime.put("Lion2", 10);
        wildAnimalsTime.put("Bear1", 20);
        wildAnimalsTime.put("Bear2", 30);
        tasks.put("Egg", 6);
        tasks.put("Chicken", 3);
        maxPrizeTime = 50;
        prize = 70;
        // -----------------------------------------------

        Mission mission = new Mission(numberOfInitialCoins, wildAnimalsTime, tasks, maxPrizeTime, prize);
        missionManager.processMission(level, mission);
    }
}
