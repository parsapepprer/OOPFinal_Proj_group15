package model;

import java.util.HashMap;

public class Mission {
    private int numberOfInitialCoins;
    private HashMap<String, Integer> domesticAnimalsTime;
    private HashMap<String, Integer> wildAnimalsTime;
    private HashMap<String, Integer> tasks;
    private int maxPrizeTime;
    private int prize;

    public Mission(int numberOfInitialCoins, HashMap<String, Integer> domesticAnimalsTime, HashMap<String, Integer> wildAnimalsTime, HashMap<String, Integer> tasks, int maxPrizeTime, int prize) {
        this.numberOfInitialCoins = numberOfInitialCoins;
        this.domesticAnimalsTime = domesticAnimalsTime;
        this.wildAnimalsTime = wildAnimalsTime;
        this.tasks = tasks;
        this.maxPrizeTime = maxPrizeTime;
        this.prize = prize;
    }
}
