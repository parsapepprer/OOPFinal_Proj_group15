package model;

import controller.ResultType;
import model.animal.Animal;
import model.good.Good;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Truck {
    private int capacity;
    private int upgradePrice;
    private HashSet<Animal> animals;
    private HashSet<Good> goods;
    private int timeRemaining;
    private int timeOfWork;
    private int loadPrice;

    public Truck() {
        capacity = 20;
        animals = new HashSet<>();
        goods = new HashSet<>();
        timeOfWork = 10;
        timeRemaining = 0;
        upgradePrice = 400;
        loadPrice = 0;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getUpgradePrice() {
        return upgradePrice;
    }

    public void upgrade() {
        capacity += 10;
        timeOfWork -= 2;
        upgradePrice += 100;
    }

    public ResultType go() {
        if (timeRemaining > 0) return ResultType.EXISTED;
        if (goods.isEmpty() && animals.isEmpty()) return ResultType.NOT_ENOUGH;
        timeRemaining = timeOfWork;
        loadPrice = calculatePrice();
        goods.clear();
        animals.clear();
        return ResultType.SUCCESS;
    }

    private int calculatePrice() {
        int loadPrice = 0;
        for (Good good : goods) {
            loadPrice += good.getPrice();
        }
        for (Animal animal : animals) {
            loadPrice += animal.getPrice();
        }
        return loadPrice;
    }

    public boolean isWorking() {
        return timeRemaining > 0;
    }

    public boolean addAnimal(HashSet<Animal> wildSet) {
        int space = 0;
        for (Animal animal : wildSet) {
            space += animal.getSpace();
        }
        if (space > capacity) return false;
        capacity -= space;
        animals.addAll(wildSet);
        return true;
    }

    public boolean addGood(HashSet<Good> goodSet) {
        int space = 0;
        for (Good good : goodSet) {
            space += good.getSpace();
        }
        if (space > capacity) return false;
        capacity -= space;
        goods.addAll(goodSet);
        return true;
    }

    public HashSet<Animal> getAnimal(String name, int number) {
        HashSet<Animal> removed = new HashSet<>();
        int space = 0;
        for (Animal animal : animals) {
            if (animal.getClass().getSimpleName().equalsIgnoreCase(name)) {
                removed.add(animal);
                space += animal.getSpace();
                number--;
                if (number == 0) break;
            }
        }
        if (number != 0) return null;
        animals.removeAll(removed);
        capacity += space;
        return removed;
    }

    public int getAnimalSpace(String name, int number) {
        int space = 0;
        for (Animal animal : animals) {
            if (animal.getClass().getSimpleName().equalsIgnoreCase(name)) {
                space += animal.getSpace();
                number--;
                if (number == 0) break;
            }
        }
        if (number != 0) return -1;
        return space;
    }

    public HashSet<Good> getGood(String name, int number) {
        HashSet<Good> removed = new HashSet<>();
        int space = 0;
        for (Good good : goods) {
            if (good.getClass().getSimpleName().equalsIgnoreCase(name)) {
                removed.add(good);
                space += good.getSpace();
                number--;
                if (number == 0) break;
            }
        }
        if (number != 0) return null;
        goods.removeAll(removed);
        capacity += space;
        return removed;
    }

    public int getGoodSpace(String name, int number) {
        int space = 0;
        for (Good good : goods) {
            if (good.getClass().getSimpleName().equalsIgnoreCase(name)) {
                space += good.getSpace();
                number--;
                if (number == 0) break;
            }
        }
        if (number != 0) return -1;
        return space;
    }

    public boolean truckIsEmpty() {
        return goods.isEmpty() && animals.isEmpty();
    }

    public void update() {
        if (timeRemaining > 0) timeRemaining--;
        if (timeRemaining == 0) {
            Game.getInstance().decreaseCoin(-loadPrice);
            loadPrice = 0;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("Truck:");
        sb.append("\n").append("\t").append("Capacity = ").append(capacity).append("\n");

        sb.append("\n").append("\t").append("Animals:").append("\n");
        HashMap<String, Integer> animalsMap = new HashMap<>();
        for (Animal animal : animals) {
            String name = animal.getClass().getSimpleName();
            if (animalsMap.containsKey(name)) {
                animalsMap.replace(name, animalsMap.get(name) + 1);
            } else {
                animalsMap.put(name, 1);
            }
        }
        for (Map.Entry<String, Integer> animal : animalsMap.entrySet()) {
            sb.append("\t").append("\t").append(animal.getKey()).append(": ").append(animal.getValue()).append("\n");
        }

        sb.append("\n").append("\t").append("Goods:").append("\n");
        HashMap<String, Integer> goodsMap = new HashMap<>();
        for (Good good : goods) {
            String name = good.getClass().getSimpleName();
            if (goodsMap.containsKey(name)) {
                goodsMap.replace(name, goodsMap.get(name) + 1);
            } else {
                goodsMap.put(name, 1);
            }
        }
        for (Map.Entry<String, Integer> good : goodsMap.entrySet()) {
            sb.append("\t").append("\t").append(good.getKey()).append(": ").append(good.getValue()).append("\n");
        }
        return sb.toString();
    }
}
