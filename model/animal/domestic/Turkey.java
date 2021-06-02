package model.animal.domestic;

import model.good.Feather;

public class Turkey extends Domestic<Feather> {
    private static int NUMBER_OF_TURKEYS = 0;

    public Turkey() {
        super(200, 3, ++NUMBER_OF_TURKEYS);
    }
}