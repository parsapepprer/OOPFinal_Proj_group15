package model.animal.domestic;

import model.Game;
import model.animal.Animal;
import model.good.Good;
import model.good.GoodList;

public abstract class Domestic extends Animal {
    protected int produceTime;
    protected int produceRemainingTime;
    protected String product;

    public Domestic(int price, int produceTime, String name, String product) {
        super(price, name, 10, 5, 1);
        this.produceTime = produceTime;
        this.produceRemainingTime = produceTime;
        this.product = product;
    }

    public void move() {
        preI = i;
        preJ = j;
        if (this.lifetime > 5) move(rand.nextBoolean(), rand.nextBoolean());
        else {
            boolean direction = false;
            boolean vertical = false;
            int distance = Integer.MAX_VALUE;
            for (int ii = 0; ii < Game.SIZE; ii++) {
                for (int jj = 0; jj < Game.SIZE; jj++) {
                    if (Game.getInstance().getGrass()[ii][jj] > 0) {
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
                Game.getInstance().getDomesticAnimals(preI, preJ).remove(this);
                Game.getInstance().getDomesticAnimals(i, j).add(this);
            }
        }
    }

    public void work() {
        if (produceRemainingTime > 0) produceRemainingTime--;
        if (produceRemainingTime == 0) {
            produceRemainingTime = produceTime;
            GoodList goodList = GoodList.getGood(product);
            try {
                Good good = (Good) Class.forName(goodList.getPackageName()).newInstance();
                good.setPlace(i, j);
                Game.getInstance().getGoods(i, j).add(good);
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ignored) {
            }
        }

        if (lifetime > 0) lifetime--;
        if (lifetime == 0) {
            Game.getInstance().getDomesticAnimals(i, j).remove(this);
            Game.getInstance().updateTask(this.getClass().getSimpleName(), false);
        }
    }

    public void eatGrass() {
        Game.getInstance().getGrass()[i][j]--;
        lifetime = 10;
    }

    public boolean isHungry() {
        return lifetime <= 5;
    }

    public boolean isLessThan(Domestic that) {
        return this.lifetime < that.lifetime;
    }

    @Override
    public String toString() {
        return name + " " + lifetime * 10 + "% " + "[" + (i + 1) + " " + (j + 1) + "]";
    }
}
