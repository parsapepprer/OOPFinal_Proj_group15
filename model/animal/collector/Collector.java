package model.animal.collector;

import model.Game;
import model.animal.Animal;
import model.good.Good;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public abstract class Collector extends Animal {

    public Collector(int price) {
        super(price, Integer.MAX_VALUE, Integer.MAX_VALUE, 1);
    }

    public void move() {
        preI = i;
        preJ = j;
        boolean horizontal = false;
        boolean vertical = false;
        int distance = Integer.MAX_VALUE;

        ArrayList<Integer> list1 = new ArrayList<>();
        ArrayList<Integer> list2 = new ArrayList<>();
        ArrayList<Integer> listI = new ArrayList<>();
        ArrayList<Integer> listJ = new ArrayList<>();
        for (int k = 0; k < Game.SIZE; k++) {
            list1.add(k);
            list2.add(k);
        }
        for (int k = Game.SIZE; k > 0; k--) {
            listI.add(list1.remove(rand.nextInt(k)));
            listJ.add(list2.remove(rand.nextInt(k)));
        }

        int capacity = Game.getInstance().getWarehouse().getCapacity();
        for (Integer ii : listI) {
            for (Integer jj : listJ) {
                for (Good good : Game.getInstance().getGoods(ii, jj)) {
                    int dis = Math.abs(ii - i) + Math.abs(jj - j);
                    if (capacity >= good.getSpace()) {
                        if (dis < distance) {
                            distance = dis;
                            vertical = Math.abs(ii - i) > Math.abs(jj - j);
                            horizontal = (ii - i) + (jj - j) > 0;
                        }
                        break;
                    }
                }
            }
        }

        if (distance == Integer.MAX_VALUE) super.move(rand.nextBoolean(), rand.nextBoolean());
        else if (distance != 0) super.move(vertical, horizontal);

        if (distance != 0) {
            Game.getInstance().getCollectorAnimals(preI, preJ).remove(this);
            Game.getInstance().getCollectorAnimals(i, j).add(this);
        }
    }

    public void work() {
        for (Good good : new HashSet<>(Game.getInstance().getGoods(i, j))) {
            if (Game.getInstance().getWarehouse().addGood(new HashSet<>(Collections.singletonList(good)))) {
                Game.getInstance().getGoods(i, j).remove(good);
                Game.getInstance().updateTask(good.getClass().getSimpleName(), true);
            }
        }
    }
}
