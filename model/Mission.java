package model;

import java.util.HashMap;
import java.util.HashSet;

public class Mission {
    private int level;
    private int numberOfInitialCoins;
    private HashSet<String> domesticAnimals;
    private HashSet<String> protectiveAnimals;
    private HashSet<String> collectorAnimals;
    private HashSet<String> factories;
    private HashMap<String, Integer> wildAnimalsTime;
    private HashMap<String, Integer> tasks;
    private int maxPrizeTime;
    private int prize;

    public Mission(int level, int numberOfInitialCoins, HashSet<String> domesticAnimals, HashSet<String> protectiveAnimals, HashSet<String> collectorAnimals, HashSet<String> factories, HashMap<String, Integer> wildAnimalsTime, HashMap<String, Integer> tasks, int maxPrizeTime, int prize) {
        this.level = level;
        this.numberOfInitialCoins = numberOfInitialCoins;
        this.domesticAnimals = domesticAnimals;
        this.protectiveAnimals = protectiveAnimals;
        this.collectorAnimals = collectorAnimals;
        this.factories = factories;
        this.wildAnimalsTime = wildAnimalsTime;
        this.tasks = tasks;
        this.maxPrizeTime = maxPrizeTime;
        this.prize = prize;
    }

    public int getLevel() {
        return level;
    }

    public int getNumberOfInitialCoins() {
        return numberOfInitialCoins;
    }

    public HashSet<String> getDomesticAnimals() {
        return domesticAnimals;
    }

    public HashSet<String> getProtectiveAnimals() {
        return protectiveAnimals;
    }

    public HashSet<String> getCollectorAnimals() {
        return collectorAnimals;
    }

    public HashSet<String> getFactories() {
        return factories;
    }

    public HashMap<String, Integer> getWildAnimalsTime() {
        return wildAnimalsTime;
    }

    public HashMap<String, Integer> getTasks() {
        return tasks;
    }

    public int getMaxPrizeTime() {
        return maxPrizeTime;
    }

    public int getPrize() {
        return prize;
    }
}
