package model.animal.domestic;

import model.good.Milk;

public class Buffalo extends Domestic<Milk> {
    private static int NUMBER_OF_BUFFALOES = 0;

    public Buffalo() {
        super(400, 5, ++NUMBER_OF_BUFFALOES);
    }
}