package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MissionManager {

    private final ArrayList<Mission> missions;

    private MissionManager(ArrayList<Mission> missions) {
        this.missions = missions;
    }

    private static MissionManager missionManagerInstance;

    public static MissionManager getInstance() {
        if (missionManagerInstance == null) {
            load();
        }
        return missionManagerInstance;
    }

    private void processMission(int level, Mission mission) {
        if (level <= missions.size() && level > 0) {
            missions.set(level - 1, mission);
            Logger.log("admin", "The missions of the level " + level + " was changed!");
            System.out.println("Successful change!");
            save();
        } else if (level == missions.size() + 1) {
            missions.add(level - 1, mission);
            Logger.log("admin", "A new level (" + level + ") was added to levels!");
            System.out.println("Successful add!");
            save();
        } else {
            Logger.log("admin", "The level admin entered was invalid!");
            System.out.println("The level is invalid!");
        }
    }

    public Mission getMission(int level) {
        return missions.get(level - 1);
    }

    public int getNumberOfLevels() {
        return missions.size();
    }

    private static void load() {
        String missionsText = FileManager.read("missions.json");
        if (!missionsText.isEmpty()) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            ArrayList<Mission> missions = gson.fromJson(missionsText, new TypeToken<ArrayList<Mission>>() {
            }.getType());
            missionManagerInstance = new MissionManager(missions);
        } else {
            missionManagerInstance = new MissionManager(new ArrayList<>());
        }
    }

    private void save() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String missionsText = gson.toJson(missions);
        FileManager.write("missions.json", missionsText);
    }

    public static boolean checkCorrectAnimals(HashSet<String> animals, String animalGroup) {
        HashMap<String, Integer[]> animalMap = new HashMap<>();

        for (String animal : animals) {
            Matcher matcher = Pattern.compile("^\\s*([a-zA-Z]+)\\s*([0-9]+)\\s*$").matcher(animal);

            if (matcher.find()) {
                String name = matcher.group(1);
                int number = Integer.parseInt(matcher.group(2));

                try {
                    if (!(Class.forName("model.animal.Animal").isInstance(Class.forName("model.animal." + animalGroup+ "." + name).newInstance()))) return false;
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                    return false;
                }

                if (number == 0) return false;
                if (animalMap.containsKey(name)) {
                    animalMap.get(name)[0]++;
                    if (number > animalMap.get(name)[1])
                        animalMap.get(name)[1] = number;
                } else {
                    animalMap.put(name, new Integer[]{1, number});
                }
            } else return false;
        }

        for (Integer[] integers : animalMap.values()) {
            if (!integers[0].equals(integers[1])) return false;
        }

        return true;
    }

    public static boolean checkCorrectFactories(HashSet<String> factories) {
        HashSet<String> factorySet = new HashSet<>();

        for (String factory : factories) {
            Matcher matcher = Pattern.compile("^\\s*([a-zA-Z]+)\\s*$").matcher(factory);

            if (matcher.find()) {
                String name = matcher.group(1);

                try {
                    if (!(Class.forName("model.factory.Factory").isInstance(Class.forName("model.factory." + name).newInstance()))) return false;
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                    return false;
                }

                if (factorySet.contains(name)) {
                    return false;
                } else {
                    factorySet.add(name);
                }
            } else return false;
        }
        return true;
    }

    public static boolean checkCorrectTasks(HashSet<String> tasks) {
        HashSet<String> taskSet = new HashSet<>();

        for (String task : tasks) {
            Matcher matcher = Pattern.compile("^\\s*([a-zA-Z]+)\\s*$").matcher(task);

            if (matcher.find()) {
                String name = matcher.group(1);

                boolean check = false;
                try {
                    if (Class.forName("model.animal.Animal").isInstance(Class.forName("model.animal.domestic." + name).newInstance())) check = true;
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ignored) {
                }
                try {
                    if (Class.forName("model.animal.Animal").isInstance(Class.forName("model.animal.wild." + name).newInstance())) check = true;
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ignored) {
                }
                try {
                    if (Class.forName("model.animal.Animal").isInstance(Class.forName("model.animal.protective." + name).newInstance())) check = true;
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ignored) {
                }
                try {
                    if (Class.forName("model.animal.Animal").isInstance(Class.forName("model.animal.collector." + name).newInstance())) check = true;
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ignored) {
                }
                try {
                    if (Class.forName("model.factory.Factory").isInstance(Class.forName("model.factory." + name).newInstance())) check = true;
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ignored) {
                }
                try {
                    if (Class.forName("model.good.Good").isInstance(Class.forName("model.good." + name).newInstance())) check = true;
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ignored) {
                }
                if (name.equalsIgnoreCase("coin")) check = true;
                if (!check) return false;

                if (taskSet.contains(name)) {
                    return false;
                } else {
                    taskSet.add(name);
                }
            } else return false;
        }
        return true;
    }

    public static void main(String[] args) {

        int level;
        int numberOfInitialCoins;
        HashSet<String> domesticAnimals = new HashSet<>();
        HashSet<String> protectiveAnimals = new HashSet<>();
        HashSet<String> collectorAnimals = new HashSet<>();
        HashSet<String> factories = new HashSet<>();
        HashMap<String, Integer> wildAnimalsTime = new HashMap<>();
        HashMap<String, Integer> tasks = new HashMap<>();
        int maxPrizeTime;
        int prize;

        // ---------- Enter the Specifications -----------
        level = 1;

        numberOfInitialCoins = 100;

        domesticAnimals.add("Chicken3");
        domesticAnimals.add("Buffalo2");
        domesticAnimals.add("Chicken1");
        domesticAnimals.add("Chicken2");
        domesticAnimals.add("Buffalo1");
        domesticAnimals.add("Chicken4");

        protectiveAnimals.add("Hound1");

        collectorAnimals.add("Cat1");

        factories.add("Mill");
        factories.add("PackingWorkshop");

        wildAnimalsTime.put("Lion1", 0);
        wildAnimalsTime.put("Lion2", 10);
        wildAnimalsTime.put("Bear1", 20);
        wildAnimalsTime.put("Bear2", 30);

        tasks.put("Egg", 6);
        tasks.put("Chicken", 3);
        tasks.put("Coin", 100);

        maxPrizeTime = 50;
        prize = 70;
        // -----------------------------------------------


        if (!checkCorrectAnimals(domesticAnimals, "domestic")) {
            System.out.println("Domestic animals have some problems!");
            return;
        }
        if (!checkCorrectAnimals(protectiveAnimals, "protective")) {
            System.out.println("Protective animals have some problems!");
            return;
        }
        if (!checkCorrectAnimals(collectorAnimals, "collector")) {
            System.out.println("Collector animals have some problems!");
            return;
        }
        if (!checkCorrectAnimals(new HashSet<>(wildAnimalsTime.keySet()), "wild")) {
            System.out.println("Wild animals have some problems!");
            return;
        }
        if (!checkCorrectFactories(factories)) {
            System.out.println("Factories have some problems!");
            return;
        }
        if (!checkCorrectTasks(new HashSet<>(tasks.keySet()))) {
            System.out.println("Tasks have some problems!");
            return;
        }

        Mission mission = new Mission(level, numberOfInitialCoins, domesticAnimals, protectiveAnimals, collectorAnimals, factories, wildAnimalsTime, tasks, maxPrizeTime, prize);
        MissionManager.getInstance().processMission(level, mission);
    }
}