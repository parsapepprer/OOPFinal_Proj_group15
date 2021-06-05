package model;

import controller.UserManager;
import model.animal.collector.Collector;
import model.animal.collector.CollectorList;
import model.animal.domestic.Domestic;
import model.animal.domestic.DomesticList;
import model.animal.protective.Protective;
import model.animal.protective.ProtectiveList;
import model.animal.wild.Wild;
import model.animal.wild.WildList;
import model.factory.Factory;
import model.factory.FactoryList;
import model.good.Good;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class Game {

    private static Game gameInstance = null;

    public static Game getInstance() {
        return gameInstance;
    }

    public static void initiateGame(Mission mission, User user) {
        gameInstance = new Game(mission, user);
    }

    public static final int SIZE = 6;

    private Mission mission;
    private User user;

    private HashMap<String, Integer[]> tasks;
    private boolean isFinished;

    private int time;
    private int coin;

    private Warehouse warehouse;
    private Truck truck;

    private int[][] grass;

    private int bucketCapacity;
    private int water;
    private int fillTime;
    private int fillRemainingTime;
    private int fillPrice;
    private int upgradePrice;

    private HashSet<Factory> factories;
    private HashSet<Domestic>[][] domesticAnimals;
    private HashSet<Wild>[][] wildAnimals;
    private HashSet<Good>[][] goods;
    private HashSet<Protective>[][] protectiveAnimals;
    private HashSet<Collector>[][] collectorAnimals;

    private Game(Mission mission, User user) {
        this.mission = mission;
        this.user = user;

        tasks = new HashMap<>();
        HashMap<String, Integer> missionTasks = mission.getTasks();
        for (String s : missionTasks.keySet()) {
            tasks.put(s, new Integer[]{missionTasks.get(s), 0});
        }
        isFinished = false;

        time = 0;
        coin = mission.getNumberOfInitialCoins();
        updateTask("Coin", true);

        warehouse = new Warehouse();
        truck = new Truck();

        grass = new int[SIZE][SIZE];
        for (int[] ints : grass)
            Arrays.fill(ints, 0);

        bucketCapacity = 5;
        water = bucketCapacity;
        fillTime = 3;
        fillRemainingTime = 0;
        fillPrice = 20;
        upgradePrice = 100;

        factories = new HashSet<>();
        for (FactoryList name : mission.getFactories()) {
            try {
                factories.add((Factory) Class.forName(name.getPackageName()).newInstance());
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ignored) {
            }
        }

        domesticAnimals = defineHashSet();
        for (DomesticList name : mission.getDomesticAnimals().keySet()) {
            for (int i = 0; i < mission.getDomesticAnimals().get(name); i++) {
                try {
                    Domestic animal = (Domestic) Class.forName(name.getPackageName()).newInstance();
                    domesticAnimals[animal.getI()][animal.getJ()].add(animal);
                    updateTask(animal.getClass().getSimpleName(), true);
                } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ignored) {
                }
            }
        }

        protectiveAnimals = defineHashSet();
        for (ProtectiveList name : mission.getProtectiveAnimals().keySet()) {
            for (int i = 0; i < mission.getProtectiveAnimals().get(name); i++) {
                try {
                    Protective animal = (Protective) Class.forName(name.getPackageName()).newInstance();
                    protectiveAnimals[animal.getI()][animal.getJ()].add(animal);
                    updateTask(animal.getClass().getSimpleName(), true);
                } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ignored) {
                }
            }
        }

        collectorAnimals = defineHashSet();
        for (CollectorList name : mission.getCollectorAnimals().keySet()) {
            for (int i = 0; i < mission.getCollectorAnimals().get(name); i++) {
                try {
                    Collector animal = (Collector) Class.forName(name.getPackageName()).newInstance();
                    collectorAnimals[animal.getI()][animal.getJ()].add(animal);
                    updateTask(animal.getClass().getSimpleName(), true);
                } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ignored) {
                }
            }
        }

        wildAnimals = defineHashSet();
        loadWilds();

        goods = defineHashSet();

        checkTaskFinished();
    }

    private HashSet[][] defineHashSet() {
        HashSet[][] parameters = new HashSet[SIZE][SIZE];
        for (int i = 0; i < parameters.length; i++) {
            for (int j = 0; j < parameters[i].length; j++) {
                parameters[i][j] = new HashSet<>();
            }
        }
        return parameters;
    }

    public void upgradeWell() {
        bucketCapacity += 1;
        fillPrice -= 2;
        upgradePrice += 50;
    }

    public int getUpgradePrice() {
        return upgradePrice;
    }

    public void loadWilds() {
        for (WildList name : mission.getWildAnimalsTime().keySet()) {
            for (int i = 0; i < mission.getWildAnimalsTime().get(name).length; i++) {
                if (mission.getWildAnimalsTime().get(name)[i] == time) {
                    try {
                        Wild animal = (Wild) Class.forName(name.getPackageName()).newInstance();
                        wildAnimals[animal.getI()][animal.getJ()].add(animal);
                        animal.work();
                    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ignored) {
                    }
                }
            }
        }
    }

    public void updateTask(String name, boolean isAdd) {
        for (String task : tasks.keySet()) {
            Integer[] values = tasks.get(task);
            if (task.equals(name) && !values[1].equals(values[0])) {
                if (task.equals("Coin")) {
                    values[1] = coin;
                    if (values[1] >= values[0]) {
                        values[1] = values[0];
                    }
                } else {
                    if (isAdd) {
                        values[1]++;
                    } else if (values[1] > 0) {
                        values[1]--;
                    }
                }
            }
        }
    }

    public boolean checkTaskFinished() {
        for (String task : tasks.keySet()) {
            Integer[] values = tasks.get(task);
            if (!values[1].equals(values[0])) {
                return false;
            }
        }
        isFinished = true;
        if (time <= mission.getMaxPrizeTime()) coin += mission.getPrize();
        UserManager.getInstance().updateUser(user, mission.getLevel() + 1, coin);
        return true;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public Mission getMission() {
        return mission;
    }

    public void wellStart() {
        fillRemainingTime = fillTime;
    }

    public int getWater() {
        return water;
    }

    public int getFillPrice() {
        return fillPrice;
    }

    public boolean wellIsWorking() {
        return fillRemainingTime > 0;
    }

    public void plant(int i, int j) {
        water--;
        grass[i][j]++;
    }

    public HashSet<Wild> getWildAnimals(int i, int j) {
        return wildAnimals[i][j];
    }

    public HashSet<Good> getGoods(int i, int j) {
        return goods[i][j];
    }

    public HashSet<Domestic> getDomesticAnimals(int i, int j) {
        return domesticAnimals[i][j];
    }

    public HashSet<Protective> getProtectiveAnimals(int i, int j) {
        return protectiveAnimals[i][j];
    }

    public HashSet<Collector> getCollectorAnimals(int i, int j) {
        return collectorAnimals[i][j];
    }

    public HashMap<String, Integer[]> getTasks() {
        return tasks;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public Truck getTruck() {
        return truck;
    }

    public int getCoin() {
        return coin;
    }

    public int getTime() {
        return time;
    }

    public int[][] getGrass() {
        return grass;
    }

    public void addDomesticAnimal(Domestic domestic) {
        coin -= domestic.getPrice();
        updateTask("Coin", true);
        domesticAnimals[domestic.getI()][domestic.getJ()].add(domestic);
        updateTask(domestic.getClass().getSimpleName(), true);
    }

    public void addProtectiveAnimal(Protective protective) {
        coin -= protective.getPrice();
        updateTask("Coin", true);
        protectiveAnimals[protective.getI()][protective.getJ()].add(protective);
        updateTask(protective.getClass().getSimpleName(), true);
        protective.work();
    }

    public void addCollectorAnimal(Collector collector) {
        coin -= collector.getPrice();
        updateTask("Coin", true);
        collectorAnimals[collector.getI()][collector.getJ()].add(collector);
        updateTask(collector.getClass().getSimpleName(), true);
        collector.work();
    }

    public void addFactory(Factory factory) {
        coin -= factory.getPrice();
        updateTask("Coin", true);
        factories.add(factory);
    }

    public HashSet<Factory> getFactories() {
        return factories;
    }

    public int getDomesticSpace(String name, int number) {
        int space = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                for (Domestic domestic : domesticAnimals[i][j]) {
                    if (domestic.getClass().getSimpleName().equalsIgnoreCase(name)) {
                        space += domestic.getSpace();
                        number--;
                        if (number == 0) break;
                    }
                }
                if (number == 0) break;
            }
            if (number == 0) break;
        }
        if (number != 0) return -1;
        return space;
    }

    public HashSet<Domestic> getDomestic(String name, int number) {
        HashSet<Domestic> removed = new HashSet<>();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                for (Domestic domestic : domesticAnimals[i][j]) {
                    if (domestic.getClass().getSimpleName().equalsIgnoreCase(name)) {
                        removed.add(domestic);
                        number--;
                        if (number == 0) break;
                    }
                }
                domesticAnimals[i][j].removeAll(removed);
                if (number == 0) break;
            }
            if (number == 0) break;
        }
        if (number != 0) return null;
        return removed;
    }

    public void unloadDomestic(HashSet<Domestic> domestics) {
        for (Domestic domestic : domestics) {
            domesticAnimals[domestic.getI()][domestic.getJ()].add(domestic);
            updateTask(domestic.getClass().getSimpleName(), true);
        }
    }

    public void decreaseCoin(int price) {
        coin -= price;
        updateTask("Coin", true);
    }

    public void increaseTime() {
        time++;
    }

    public void updateWell() {
        if (fillRemainingTime > 0) fillRemainingTime--;
        if (fillRemainingTime == 0) {
            water = bucketCapacity;
        }
    }
}
