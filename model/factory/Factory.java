package model.factory;

import controller.ResultType;
import model.Game;
import model.good.Good;
import model.good.GoodList;

import java.util.HashSet;

public abstract class Factory {
    protected int price;
    protected int upgradePrice;
    protected int level;
    protected int produceTime;
    protected int produceRemainingTime;
    protected String input;
    protected String output;

    public Factory(int price, int produceTime, String input, String output) {
        this.price = price;
        this.upgradePrice = price;
        this.level = 1;
        this.produceTime = produceTime;
        this.produceRemainingTime = 0;
        this.input = input;
        this.output = output;
    }

    public int getPrice() {
        return price;
    }

    public void upgrade() {
        level++;
        upgradePrice += 100;
    }

    public ResultType work(int number) {
        if (produceRemainingTime > 0) return ResultType.EXISTED;
        if (number > level || number <= 0) return ResultType.INVALID_NUMBER;
        HashSet<Good> goods = Game.getInstance().getWarehouse().getGood(input, number);
        if (goods == null) return ResultType.NOT_ENOUGH;
        produceRemainingTime = produceTime * number / level + (((produceTime * number) % level == 0) ? 0 : 1);
        return ResultType.SUCCESS;
    }

    public void update() {
        if (produceRemainingTime > 0) produceRemainingTime--;
        if (produceRemainingTime == 0) {
            GoodList goodList = GoodList.getGood(output);
            try {
                Good good = (Good) Class.forName(goodList.getPackageName()).newInstance();
                Game.getInstance().getGoods(good.getI(), good.getJ()).add(good);
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ignored) {
            }
        }
    }
}
