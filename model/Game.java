package model;

import model.animal.Animal;
import model.animal.collector.Collector;
import model.animal.domestic.Domestic;
import model.animal.protective.Protective;
import model.animal.wild.Wild;
import model.factory.Factory;
import model.good.Good;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Game {
    public static final int WATER_BUCKET_CAPACITY = 5;
    public static final int DURATION_OF_WELL_WORKING = 3;

    private Mission mission;
    private User user;

    private int time;
    private int coin;

    private int[][] grass;
    private int water;
    private boolean wellIsWorking;
    private int remainingTimeToFillBucket;

    private HashSet<Factory> factories;
    private HashSet<Domestic>[][] domesticAnimals;
    private HashSet<Wild>[][] wildAnimals;
    private HashSet<Good>[][] goods;
    private HashSet<Protective>[][] protectiveAnimals;
    private HashSet<Collector>[][] collectorAnimals;

    public Game(Mission mission, User user) {
        this.mission = mission;
        this.user = user;

        time = 0;
        coin = mission.getNumberOfInitialCoins();

        grass = new int[6][6];
        for (int[] ints : grass)
            Arrays.fill(ints, 0);

        water = WATER_BUCKET_CAPACITY;
        wellIsWorking = false;
        remainingTimeToFillBucket = 0;

        factories = new HashSet<>();
        readMissionFactories(factories, mission.getFactories());

        domesticAnimals = defineHashSet();
        readMissionNonWildAnimal(domesticAnimals, mission.getDomesticAnimals(), "domestic");

        protectiveAnimals = defineHashSet();
        readMissionNonWildAnimal(protectiveAnimals, mission.getProtectiveAnimals(), "protective");

        collectorAnimals = defineHashSet();
        readMissionNonWildAnimal(collectorAnimals, mission.getCollectorAnimals(), "collector");

        wildAnimals = defineHashSet();
        readMissionWildAnimal(wildAnimals, mission.getWildAnimalsTime());

        System.out.println("ok");
    }

    private HashSet[][] defineHashSet() {
        HashSet[][] parameters = new HashSet[6][6];
        for (int i = 0; i < parameters.length; i++) {
            for (int j = 0; j < parameters[i].length; j++) {
                parameters[i][j] = new HashSet<>();
            }
        }
        return parameters;
    }

    private void readMissionFactories(HashSet<Factory> parameters, HashSet<String> parametersName) {
        for (String name : parametersName) {
            try {
                parameters.add((Factory) Class.forName("model.factory." + name).newInstance());
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ignored) {
            }
        }
    }

    private void readMissionNonWildAnimal(HashSet[][] parameters, HashSet<String> parametersName, String packageName) {
        for (String name : parametersName) {
            try {
                Matcher matcher = Pattern.compile("^\\s*([a-zA-Z]+)\\s*([0-9]+)\\s*$").matcher(name);
                if (matcher.find()) {
                    Animal animal = (Animal) Class.forName("model.animal." + packageName + "." + matcher.group(1)).newInstance();
                    parameters[animal.getY()][animal.getX()].add(animal);
                }
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ignored) {
            }
        }
    }

    private void readMissionWildAnimal(HashSet[][] parameters, HashMap<String, Integer> parametersNameTime) {
        for (String name : parametersNameTime.keySet()) {
            try {
                if (parametersNameTime.get(name) == time) {
                    Matcher matcher = Pattern.compile("^\\s*([a-zA-Z]+)\\s*([0-9]+)\\s*$").matcher(name);
                    if (matcher.find()) {
                        Wild wild = (Wild) Class.forName("model.animal.wild." + matcher.group(1)).newInstance();
                        parameters[wild.getY()][wild.getX()].add(wild);
                    }
                }
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ignored) {
            }
        }
    }
}
