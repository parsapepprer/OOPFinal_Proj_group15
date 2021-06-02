package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.*;

import java.util.ArrayList;
import java.util.HashMap;

public class MissionManager {

    public static int number_of_levels = 0;
    private final HashMap<Integer, Mission> missions;

    private MissionManager(HashMap<Integer, Mission> missions) {
        this.missions = missions;
        number_of_levels = missions.size();
    }

    private static MissionManager missionManagerInstance;

    public static MissionManager getInstance() {
        if (missionManagerInstance == null) {
            load();
        }
        return missionManagerInstance;
    }

    private void processMission(int level, Mission mission) {
        if (level <= missions.size() && level >= 0) {
            missions.replace(level, mission);
            Logger.log("admin", "The missions of level " + level + " was changed!");
            save();
        } else if (level == missions.size() + 1) {
            missions.put(level, mission);
            number_of_levels++;
            Logger.log("admin", "a new level (" + level + ") was added to levels!");
            save();
        }
    }

    public Mission getMission(int level) {
        return missions.get(level);
    }

    private static void load() {
        String missionsText = FileManager.read("missions.json");
        if (!missionsText.isEmpty()) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            HashMap<Integer, Mission> missions = gson.fromJson(missionsText, new TypeToken<HashMap<Integer, Mission>>() {
            }.getType());
            missionManagerInstance = new MissionManager(missions);
        } else {
            missionManagerInstance = new MissionManager(new HashMap<>());
        }
    }

    private void save() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String missionsText = gson.toJson(this);
        FileManager.write("missions.json", missionsText);
    }

    public static void main(String[] args) {

        int level;
        int numberOfInitialCoins;
        HashMap<String, Integer> domesticAnimalsTime = new HashMap<>();
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

        Mission mission = new Mission(numberOfInitialCoins, domesticAnimalsTime, wildAnimalsTime, tasks, maxPrizeTime, prize);
        MissionManager.getInstance().processMission(level, mission);
    }
}
