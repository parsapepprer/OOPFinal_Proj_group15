package controller;

import model.Game;
import model.User;
import model.animal.Animal;
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
import model.good.GoodList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;

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
        Game.initiateGame(MissionManager.getInstance().getMission(level), user);
        this.game = Game.getInstance();
    }

    public ResultType well() {
        if (game.getWater() > 0) return ResultType.BAD_CONDITION;
        if (game.wellIsWorking()) return ResultType.EXISTED;
        if (game.getFillPrice() > game.getCoin()) return ResultType.NOT_ENOUGH;
        game.decreaseCoin(game.getFillPrice());
        game.wellStart();
        return ResultType.SUCCESS;
    }

    public ResultType plant(int i, int j) {
        if (i <= 0 || j <= 0 || i > Game.SIZE || j > Game.SIZE) {
            return ResultType.INVALID_NUMBER;
        }
        if (game.getWater() <= 0) return ResultType.NOT_ENOUGH;
        game.plant(i - 1, j - 1);
        return ResultType.SUCCESS;
    }

    public ResultType pickup(int i, int j) {
        if (i <= 0 || j <= 0 || i > Game.SIZE || j > Game.SIZE) {
            return ResultType.INVALID_NUMBER;
        }

        if (game.getWildAnimals(i - 1, j - 1).isEmpty() && game.getGoods(i - 1, j - 1).isEmpty()) return ResultType.NOT_EXISTED;

        for (Wild wild : game.getWildAnimals(i - 1, j - 1)) {
            if (wild.isInCage() && game.getWarehouse().addWild(new HashSet<>(Collections.singletonList(wild)))) {
                game.getWildAnimals(i - 1, j - 1).remove(wild);
                game.updateTask(wild.getClass().getSimpleName(), true);
            }
        }

        for (Good good : game.getGoods(i - 1, j - 1)) {
            if (game.getWarehouse().addGood(new HashSet<>(Collections.singletonList(good)))) {
                game.getGoods(i - 1, j - 1).remove(good);
                game.updateTask(good.getClass().getSimpleName(), true);
            }
        }

        if (game.checkTaskFinished()) {
            ResultType result = ResultType.FINISH;
            StringBuilder sb = new StringBuilder();
            sb.append("You completed the level ").append(game.getMission().getLevel()).append("!\n");
            sb.append("Time = ").append(game.getTime()).append("\n");
            if (game.getTime() <= game.getMission().getMaxPrizeTime()) {
                sb.append("Coin = ").append(game.getCoin() - game.getMission().getPrize()).append("\n");
                sb.append("Prize for finishing in time: ").append(game.getMission().getPrize()).append(" Coins\n");
            } else {
                sb.append("Coin = ").append(game.getCoin());
                sb.append("Prize for finishing in time: 0 Coins\n");
            }
            sb.append("You collected ").append(game.getCoin()).append(" Coins in this level!");
            result.setValue(sb.toString());
            return result;
        }

        return ResultType.SUCCESS;
    }

    public ResultType buy(String name) {
        DomesticList domesticList = DomesticList.getDomestic(name);
        if (domesticList != null) {
            try {
                if (domesticList.getPrice() > game.getCoin()) {
                    return ResultType.NOT_ENOUGH;
                } else {
                    game.addDomesticAnimal((Domestic) Class.forName(domesticList.getPackageName()).newInstance());
                    return ResultType.SUCCESS;
                }
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ignored) {
            }
        }

        ProtectiveList protectiveList = ProtectiveList.getProtective(name);
        if (protectiveList != null) {
            try {
                if (protectiveList.getPrice() > game.getCoin()) {
                    return ResultType.NOT_ENOUGH;
                } else {
                    game.addProtectiveAnimal((Protective) Class.forName(protectiveList.getPackageName()).newInstance());
                    return ResultType.SUCCESS;
                }
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ignored) {
            }
        }

        CollectorList collectorList = CollectorList.getCollector(name);
        if (collectorList != null) {
            try {
                if (collectorList.getPrice() > game.getCoin()) {
                    return ResultType.NOT_ENOUGH;
                } else {
                    game.addCollectorAnimal((Collector) Class.forName(collectorList.getPackageName()).newInstance());
                    return ResultType.SUCCESS;
                }
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ignored) {
            }
        }

        if (game.checkTaskFinished()) {
            ResultType result = ResultType.FINISH;
            StringBuilder sb = new StringBuilder();
            sb.append("You completed the level ").append(game.getMission().getLevel()).append("!\n");
            sb.append("Time = ").append(game.getTime()).append("\n");
            if (game.getTime() <= game.getMission().getMaxPrizeTime()) {
                sb.append("Coin = ").append(game.getCoin() - game.getMission().getPrize()).append("\n");
                sb.append("Prize for finishing in time: ").append(game.getMission().getPrize()).append(" Coins\n");
            } else {
                sb.append("Coin = ").append(game.getCoin());
                sb.append("Prize for finishing in time: 0 Coins\n");
            }
            sb.append("You collected ").append(game.getCoin()).append(" Coins in this level!");
            result.setValue(sb.toString());
            return result;
        }

        return ResultType.NOT_EXISTED;
    }

    public ResultType build(String name) {
        FactoryList factoryList = FactoryList.getFactory(name);
        if (factoryList != null) {
            for (Factory factory : game.getFactories()) {
                if (factory.getClass().getSimpleName().equalsIgnoreCase(factoryList.getClassName())) {
                    return ResultType.EXISTED;
                }
            }
            try {
                if (factoryList.getPrice() > game.getCoin()) {
                    return ResultType.NOT_ENOUGH;
                } else {
                    game.addFactory((Factory) Class.forName(factoryList.getPackageName()).newInstance());
                    return ResultType.SUCCESS;
                }
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ignored) {
            }
        }
        return ResultType.NOT_EXISTED;
    }

    public ResultType work(String name, int number) {
        FactoryList factoryList = FactoryList.getFactory(name);
        if (factoryList != null) {
            for (Factory factory : game.getFactories()) {
                if (factory.getClass().getSimpleName().equalsIgnoreCase(factoryList.getClassName())) {
                    return factory.work(number);
                }
            }
        }
        return ResultType.NOT_EXISTED;
    }

    public ResultType cage(int i, int j) {
        if (i <= 0 || j <= 0 || i > Game.SIZE || j > Game.SIZE) {
            return ResultType.INVALID_NUMBER;
        }

        if (game.getWildAnimals(i - 1, j - 1).isEmpty()) return ResultType.NOT_EXISTED;

        for (Wild wild : game.getWildAnimals(i - 1, j - 1)) {
            if (!wild.isInCage()) {
                wild.addCage();
            }
        }

        return ResultType.SUCCESS;
    }

    public ResultType truckLoad(String name, int number) {
        if (game.getTruck().isWorking()) return ResultType.BAD_CONDITION;

        WildList wildList = WildList.getWild(name);
        if (wildList != null) {
            int space = game.getWarehouse().getWildSpace(wildList.getClassName(), number);
            if (space == -1) return ResultType.NOT_ENOUGH;
            if (game.getTruck().getCapacity() < space) {
                return ResultType.INVALID_NUMBER;
            } else {
                game.getTruck().addAnimal(new HashSet<>(game.getWarehouse().getWild(wildList.getClassName(), number)));
                return ResultType.SUCCESS;
            }
        }

        DomesticList domesticList = DomesticList.getDomestic(name);
        if (domesticList != null) {
            int space = game.getDomesticSpace(domesticList.getClassName(), number);
            if (space == -1) return ResultType.NOT_ENOUGH;
            if (game.getTruck().getCapacity() < space) {
                return ResultType.INVALID_NUMBER;
            } else {
                game.getTruck().addAnimal(new HashSet<>(game.getDomestic(domesticList.getClassName(), number)));
                for (int i = 0; i < number; i++) {
                    game.updateTask(domesticList.getClassName(), false);
                }
                return ResultType.SUCCESS;
            }
        }

        GoodList goodList = GoodList.getGood(name);
        if (goodList != null) {
            int space = game.getWarehouse().getGoodSpace(goodList.getClassName(), number);
            if (space == -1) return ResultType.NOT_ENOUGH;
            if (game.getTruck().getCapacity() < space) {
                return ResultType.INVALID_NUMBER;
            } else {
                game.getTruck().addGood(game.getWarehouse().getGood(goodList.getClassName(), number));
                return ResultType.SUCCESS;
            }
        }

        return ResultType.NOT_EXISTED;
    }

    public ResultType truckUnload(String name, int number) {
        if (game.getTruck().isWorking()) return ResultType.BAD_CONDITION;

        WildList wildList = WildList.getWild(name);
        if (wildList != null) {
            int space = game.getTruck().getAnimalSpace(wildList.getClassName(), number);
            if (space == -1) return ResultType.NOT_ENOUGH;
            if (game.getWarehouse().getCapacity() < space) {
                return ResultType.INVALID_NUMBER;
            } else {
                HashSet<Wild> wilds = new HashSet<>();
                for (Animal animal : game.getTruck().getAnimal(wildList.getClassName(), number)) {
                    wilds.add((Wild) animal);
                }
                game.getWarehouse().addWild(wilds);
                return ResultType.SUCCESS;
            }
        }

        DomesticList domesticList = DomesticList.getDomestic(name);
        if (domesticList != null) {
            if (game.getTruck().getAnimalSpace(domesticList.getClassName(), number) == -1) return ResultType.NOT_ENOUGH;
            HashSet<Domestic> domestics = new HashSet<>();
            for (Animal animal : game.getTruck().getAnimal(domesticList.getClassName(), number)) {
                domestics.add((Domestic) animal);
            }
            game.unloadDomestic(domestics);
            return ResultType.SUCCESS;
        }

        GoodList goodList = GoodList.getGood(name);
        if (goodList != null) {
            int space = game.getTruck().getGoodSpace(goodList.getClassName(), number);
            if (space == -1) return ResultType.NOT_ENOUGH;
            if (game.getWarehouse().getCapacity() < space) {
                return ResultType.INVALID_NUMBER;
            } else {
                game.getWarehouse().addGood(game.getTruck().getGood(goodList.getClassName(), number));
                return ResultType.SUCCESS;
            }
        }

        return ResultType.NOT_EXISTED;
    }

    public ResultType truckGo() {
        return game.getTruck().go();
    }

    public String inquiry() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("Time = ").append(game.getTime()).append("\n");
        sb.append("\n").append("Grass:\n");
        int[][] grass = game.getGrass();
        for (int i = 0; i < Game.SIZE; i++) {
            sb.append("\t");
            for (int j = 0; j < Game.SIZE; j++) {
                sb.append(String.format("%3d", grass[i][j]));
            }
            sb.append("\n");
        }
        sb.append("\n").append("Domestics:\n");
        for (int i = 0; i < Game.SIZE; i++) {
            for (int j = 0; j < Game.SIZE; j++) {
                for (Animal animal : game.getDomesticAnimals(i, j)) {
                    sb.append("\t").append(animal.toString()).append("\n");
                }
            }
        }
        sb.append("\n").append("Wilds:\n");
        for (int i = 0; i < Game.SIZE; i++) {
            for (int j = 0; j < Game.SIZE; j++) {
                for (Animal animal : game.getWildAnimals(i, j)) {
                    sb.append("\t").append(animal.toString()).append("\n");
                }
            }
        }
        sb.append("\n").append("Protectives:\n");
        for (int i = 0; i < Game.SIZE; i++) {
            for (int j = 0; j < Game.SIZE; j++) {
                for (Animal animal : game.getProtectiveAnimals(i, j)) {
                    sb.append("\t").append(animal.toString()).append("\n");
                }
            }
        }
        sb.append("\n").append("Collectors:\n");
        for (int i = 0; i < Game.SIZE; i++) {
            for (int j = 0; j < Game.SIZE; j++) {
                for (Animal animal : game.getCollectorAnimals(i, j)) {
                    sb.append("\t").append(animal.toString()).append("\n");
                }
            }
        }
        sb.append("\n").append("Goods:\n");
        for (int i = 0; i < Game.SIZE; i++) {
            for (int j = 0; j < Game.SIZE; j++) {
                for (Good good : game.getGoods(i, j)) {
                    sb.append("\t").append(good.toString()).append("\n");
                }
            }
        }
        sb.append("\n").append("Tasks:\n");
        for (Map.Entry<String, Integer[]> task : game.getTasks().entrySet()) {
            sb.append("\t").append(task.getKey()).append(": ").append(task.getValue()[1]).append("/").append(task.getValue()[0]).append("\n");
        }

        sb.append(game.getWarehouse().toString());

        sb.append(game.getTruck().toString());

        sb.delete(sb.length() - 1, sb.length());
        return sb.toString();
    }

    public ResultType upgrade(String name) {
        FactoryList factoryList = FactoryList.getFactory(name);
        if (factoryList != null) {
            for (Factory factory : game.getFactories()) {
                if (factory.getClass().getSimpleName().equalsIgnoreCase(factoryList.getClassName())) {
                    if (factory.getPrice() > game.getCoin()) {
                        return ResultType.INVALID_NUMBER;
                    } else {
                        game.decreaseCoin(factory.getPrice());
                        factory.upgrade();
                        ResultType result = ResultType.SUCCESS;
                        result.setValue(factory.getClass().getSimpleName());
                        return result;
                    }

                }
            }
        }

        if (name.matches("^(?i)\\s*truck\\s*$")) {
            if (game.getTruck().getUpgradePrice() > game.getCoin()) {
                return ResultType.INVALID_NUMBER;
            } else {
                game.decreaseCoin(game.getTruck().getUpgradePrice());
                game.getTruck().upgrade();
                ResultType result = ResultType.SUCCESS;
                result.setValue("Truck");
                return result;
            }
        }

        if (name.matches("^(?i)\\s*ware\\s*house\\s*$")) {
            if (game.getWarehouse().getUpgradePrice() > game.getCoin()) {
                return ResultType.NOT_ENOUGH;
            } else {
                game.decreaseCoin(game.getWarehouse().getUpgradePrice());
                game.getWarehouse().upgrade();
                ResultType result = ResultType.SUCCESS;
                result.setValue("Warehouse");
                return result;
            }
        }

        if (name.matches("^(?i)\\s*well\\s*$")) {
            if (game.getUpgradePrice() > game.getCoin()) {
                return ResultType.NOT_ENOUGH;
            } else {
                game.decreaseCoin(game.getUpgradePrice());
                game.upgradeWell();
                ResultType result = ResultType.SUCCESS;
                result.setValue("Well");
                return result;
            }
        }

        return ResultType.NOT_EXISTED;
    }

    public ResultType turn(int n) {
        if (n <= 0) return ResultType.INVALID_NUMBER;
        for (int i = 0; i < n; i++) {
            update();
            if (game.checkTaskFinished()) {
                ResultType result = ResultType.FINISH;
                StringBuilder sb = new StringBuilder();
                sb.append("You completed the level ").append(game.getMission().getLevel()).append("!\n");
                sb.append("Time = ").append(game.getTime()).append("\n");
                if (game.getTime() <= game.getMission().getMaxPrizeTime()) {
                    sb.append("Coin = ").append(game.getCoin() - game.getMission().getPrize()).append("\n");
                    sb.append("Prize for finishing in time: ").append(game.getMission().getPrize()).append(" Coins\n");
                } else {
                    sb.append("Coin = ").append(game.getCoin());
                    sb.append("Prize for finishing in time: 0 Coins\n");
                }
                sb.append("You collected ").append(game.getCoin()).append(" Coins in this level!");
                result.setValue(sb.toString());
                return result;
            }
        }
        ResultType result = ResultType.SUCCESS;
        result.setValue(inquiry());
        return result;
    }

    private void update() {
        game.increaseTime();
        game.updateWell();
        game.getTruck().update();
        for (int i = 0; i < Game.SIZE; i++) {
            for (int j = 0; j < Game.SIZE; j++) {
                for (Domestic domestic : game.getDomesticAnimals(i, j)) {
                    domestic.move();
                }
            }
        }
        for (int i = 0; i < Game.SIZE; i++) {
            for (int j = 0; j < Game.SIZE; j++) {
                for (Wild wild : game.getWildAnimals(i, j)) {
                    wild.move();
                }
            }
        }
        for (int i = 0; i < Game.SIZE; i++) {
            for (int j = 0; j < Game.SIZE; j++) {
                for (Protective protective : game.getProtectiveAnimals(i, j)) {
                    protective.move();
                }
            }
        }
        for (int i = 0; i < Game.SIZE; i++) {
            for (int j = 0; j < Game.SIZE; j++) {
                for (Collector collector : game.getCollectorAnimals(i, j)) {
                    collector.move();
                }
            }
        }
        game.loadWilds();
        for (int i = 0; i < Game.SIZE; i++) {
            for (int j = 0; j < Game.SIZE; j++) {
                for (Protective protective : game.getProtectiveAnimals(i, j)) {
                    protective.work();
                }
            }
        }
        for (int i = 0; i < Game.SIZE; i++) {
            for (int j = 0; j < Game.SIZE; j++) {
                for (Wild wild : game.getWildAnimals(i, j)) {
                    wild.work();
                }
            }
        }
        ArrayList<Domestic> domestics = new ArrayList<>();
        int[][] grass = game.getGrass();
        int number = 0;
        for (int i = 0; i < Game.SIZE; i++) {
            for (int j = 0; j < Game.SIZE; j++) {
                domestics.clear();
                number = grass[i][j];
                for (Domestic domestic : game.getDomesticAnimals(i, j)) {
                    if (domestic.isHungry())
                        domestics.add(domestic);
                }
                if (number >= domestics.size()) {
                    for (Domestic domestic : domestics) {
                        domestic.eatGrass();
                    }
                } else {
                    for (int k = 1; k < domestics.size(); k++) {
                        Domestic key = domestics.get(k);
                        int l = k - 1;
                        while (l >= 0 && key.isLessThan(domestics.get(l))) {
                            domestics.set(l + 1, domestics.get(l));
                            l--;
                        }
                        domestics.set(l + 1, key);
                    }
                    for (int k = 0; k < number; k++) {
                        domestics.get(k).eatGrass();
                    }
                }
            }
        }
        for (int i = 0; i < Game.SIZE; i++) {
            for (int j = 0; j < Game.SIZE; j++) {
                for (Domestic domestic : game.getDomesticAnimals(i, j)) {
                    domestic.work();
                }
            }
        }
        for (Factory factory : game.getFactories()) {
            factory.update();
        }
        for (int i = 0; i < Game.SIZE; i++) {
            for (int j = 0; j < Game.SIZE; j++) {
                for (Collector collector : game.getCollectorAnimals(i, j)) {
                    collector.work();
                }
            }
        }
        for (int i = 0; i < Game.SIZE; i++) {
            for (int j = 0; j < Game.SIZE; j++) {
                for (Good good : game.getGoods(i, j)) {
                    good.update();
                }
            }
        }
    }

    public boolean isFinished() {
        return game.isFinished();
    }

    public boolean truckIsEmpty() {
        return game.getTruck().truckIsEmpty();
    }
}
