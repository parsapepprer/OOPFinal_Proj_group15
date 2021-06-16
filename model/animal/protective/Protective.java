package model.animal.protective;

import model.Game;
import model.animal.Animal;
import model.animal.wild.Wild;

import java.util.ArrayList;
import java.util.HashSet;

public abstract class Protective extends Animal {

    public Protective(int price) {
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

        for (Integer ii : listI) {
            for (Integer jj : listJ) {
                for (Wild wild : Game.getInstance().getWildAnimals(ii, jj)) {
                    if (!wild.isInCage()) {
                        int dis = Math.abs(ii - i) + Math.abs(jj - j);
                        if (dis < distance) {
                            distance = dis;
                            vertical = Math.abs(ii - i) > Math.abs(jj - j);
                            horizontal = (ii - i) + (jj - j) > 0;
                        }
                    }
                }
            }
        }
        if (distance == Integer.MAX_VALUE) super.move(rand.nextBoolean(), rand.nextBoolean());
        else if (distance != 0) super.move(vertical, horizontal);
        if (distance != 0) {
            Game.getInstance().getProtectiveAnimals(preI, preJ).remove(this);
            Game.getInstance().getProtectiveAnimals(i, j).add(this);
        }
    }

    public void work() {
        Wild removedWild = null;
        for (Wild wild : Game.getInstance().getWildAnimals(i, j)) {
            if (!wild.isInCage()) {
                if (removedWild == null) {
                    removedWild = wild;
                } else if (wild.isGreaterThan(removedWild)) {
                    removedWild = wild;
                }
            }
        }
        if (preI != -1 && preJ != -1) {
            int size;
            if (i == preI && j == preJ) size = 2 * Game.SIZE;
            else size = Game.SIZE;
            HashSet<Wild>[] wilds = new HashSet[size];
            if (i == preI) {
                for (int k = 0; k < Game.SIZE; k++) {
                    wilds[k] = Game.getInstance().getWildAnimals(i, k);
                }
            }
            if (j == preJ) {
                for (int k = 0; k < Game.SIZE; k++) {
                    wilds[k + size - Game.SIZE] = Game.getInstance().getWildAnimals(k, j);
                }
            }
            if (wilds[0] == null) return;

            for (int k = 0; k < size; k++) {
                for (Wild wild : wilds[k]) {
                    if (!wild.isInCage() && wild.getPreI() != -1 && wild.getPreJ() != -1 && wild.encounter(this)) {
                        if (removedWild == null) {
                            removedWild = wild;
                        } else if (wild.isGreaterThan(removedWild)) {
                            removedWild = wild;
                        }
                    }
                }
            }
        }

        if (removedWild == null) return;
        Game.getInstance().getWildAnimals(removedWild.getI(), removedWild.getJ()).remove(removedWild);
        Game.getInstance().getProtectiveAnimals(i, j).remove(this);
    }
}
