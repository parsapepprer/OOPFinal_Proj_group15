package model.animal.domestic;

import model.good.Egg;

public class Chicken extends Domestic<Egg> {
    private static int NUMBER_OF_CHICKENS = 0;

    public Chicken() {
        super(100, 2, ++NUMBER_OF_CHICKENS);
    }
}
