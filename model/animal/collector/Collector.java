package model.animal.collector;

import model.Game;
import model.animal.Animal;
import model.good.Good;

import java.util.Collections;
import java.util.HashSet;

public abstract class Collector extends Animal {

    public Collector(int price, String name) {
        super(price, name, Integer.MAX_VALUE, Integer.MAX_VALUE, 1);
    }

    public void move() {
        preI = i;
        preJ = j;
        boolean direction = false;
        boolean vertical = false;
        int distance = Integer.MAX_VALUE;
        for (int ii = 0; ii < Game.SIZE; ii++) {
            for (int jj = 0; jj < Game.SIZE; jj++) {
                for (Good ignored : Game.getInstance().getGoods(ii, jj)) {
                    int dis = Math.abs(ii - i) + Math.abs(jj - j);
                    if (dis < distance) {
                        distance = dis;
                        vertical = Math.abs(ii - i) > Math.abs(jj - j);
                        direction = (ii - i) + (jj - j) > 0;
                    }
                }
            }
        }
        if (distance == Integer.MAX_VALUE) super.move(rand.nextBoolean(), rand.nextBoolean());
        else if (distance != 0) super.move(vertical, direction);
        if (distance != 0) {
            Game.getInstance().getCollectorAnimals(preI, preJ).remove(this);
            Game.getInstance().getCollectorAnimals(i, j).add(this);
        }
    }

    public void work() {
        for (Good good : Game.getInstance().getGoods(i, j)) {
            if (Game.getInstance().getWarehouse().addGood(new HashSet<>(Collections.singletonList(good)))) {
                Game.getInstance().getGoods(i, j).remove(good);
                Game.getInstance().updateTask(good.getClass().getSimpleName(), true);
            }
        }
    }
}
