package model.animal.wild;

import model.Game;
import model.animal.Animal;
import model.animal.domestic.Domestic;

import java.util.HashSet;

public abstract class Wild extends Animal {
    protected int cageNeeded;
    protected int cageRemaining;

    public Wild(int cageNeeded, int price, int step) {
        super(price, 5, 15, step);
        this.cageNeeded = cageNeeded;
        this.cageRemaining = cageNeeded;
    }

    public boolean isInCage() {
        return cageRemaining <= 0;
    }

    public void addCage() {
        if (cageRemaining > 0) cageRemaining--;
        else cageRemaining = 0;
    }

    public boolean isGreaterThan(Wild that) {
        return this.price > that.price;
    }

    public void move() {
        preI = i;
        preJ = j;
        if (!isInCage()) {
            super.move(rand.nextBoolean(), rand.nextBoolean());
            Game.getInstance().getWildAnimals(preI, preJ).remove(this);
            Game.getInstance().getWildAnimals(i, j).add(this);
        }
    }

    public void work() {
        if (isInCage()) {
            if (lifetime > 0) {
                lifetime--;
                if (lifetime == 0) {
                    Game.getInstance().getWildAnimals(i, j).remove(this);
                }
            }
        } else {
            if (cageRemaining < cageNeeded) cageRemaining++;

            Game.getInstance().getGoods(i, j).clear();
            HashSet<Domestic> removedDomestic = new HashSet<>(Game.getInstance().getDomesticAnimals(i, j));

            if (preI != -1 && preJ != -1) {
                HashSet<Domestic>[] domestics = new HashSet[Game.SIZE];

                if (i == preI) {
                    for (int k = 0; k < Game.SIZE; k++) {
                        domestics[k] = Game.getInstance().getDomesticAnimals(i, k);
                    }

                    for (int k = Math.min(preJ, j) + 1; k < Math.max(preJ, j); k++) {
                        Game.getInstance().getGoods(i, k).clear();
                    }
                }
                if (j == preJ) {
                    for (int k = 0; k < Game.SIZE; k++) {
                        domestics[k] = Game.getInstance().getDomesticAnimals(k, j);
                    }

                    for (int k = Math.min(preI, i) + 1; k < Math.max(preI, i); k++) {
                        Game.getInstance().getGoods(k, j).clear();
                    }
                }
                if (domestics[0] == null) return;

                for (int k = 0; k < Game.SIZE; k++) {
                    for (Domestic domestic : domestics[k]) {
                        if (domestic.getPreI() != -1 && domestic.getPreJ() != -1 && domestic.encounter(this)) {
                            removedDomestic.add(domestic);
                        }
                    }
                }
            }

            for (Domestic domestic : removedDomestic) {
                Game.getInstance().getDomesticAnimals(domestic.getI(), domestic.getJ()).remove(domestic);
                Game.getInstance().updateTask(domestic.getClass().getSimpleName(), false);
            }
        }
    }

    @Override
    public String toString() {
        return (isInCage() ? "* " : "") + this.getClass().getSimpleName() + " " + (isInCage() ? lifetime : cageRemaining) + " " + "[" + (i + 1) + " " + (j + 1) + "]";
    }
}
