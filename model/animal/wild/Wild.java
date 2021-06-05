package model.animal.wild;

import model.Game;
import model.animal.Animal;
import model.animal.domestic.Domestic;

import java.util.HashSet;

public abstract class Wild extends Animal {
    protected int space;
    protected int cageNeeded;
    protected int cageRemaining;

    public Wild(int cageNeeded, int price, String name, int step) {
        super(price, name, 5, 15, step);
        this.space = 15;
        this.cageNeeded = cageNeeded;
        this.cageRemaining = cageNeeded;
    }

    public int getSpace() {
        return space;
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
            if (lifetime > 0) lifetime--;
            if (lifetime == 0) {
                Game.getInstance().getWildAnimals(i, j).remove(this);
            }
        } else {
            if (cageRemaining < cageNeeded) cageRemaining++;

            Game.getInstance().getDomesticAnimals(i, j).clear();
            Game.getInstance().getGoods(i, j).clear();

            HashSet<Domestic> removedDomestic = new HashSet<>();
            if (preI != -1 && preJ != -1) {
                HashSet<Domestic>[] domestics = new HashSet[Game.SIZE];

                if (i == preI) {
                    for (int k = 0; k < Game.SIZE; k++) {
                        domestics[k] = Game.getInstance().getDomesticAnimals(i, k);
                    }
                }
                if (j == preJ) {
                    for (int k = 0; k < Game.SIZE; k++) {
                        domestics[k] = Game.getInstance().getDomesticAnimals(k, j);
                    }
                }
                if (domestics[0] == null) return;

                for (int k = 0; k < Game.SIZE; k++) {
                    for (Domestic domestic : domestics[k]) {
                        if ((domestic.getPreI() == -1 || domestic.getPreJ() == -1)) {
                            if (domestic.getI() == i && domestic.getJ() == j) {
                                removedDomestic.add(domestic);
                            }
                        } else if (domestic.intersection(this)) {
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
        return name + " " + cageRemaining + " " + "[" + (i + 1) + " " + (j + 1) + "]";
    }
}
