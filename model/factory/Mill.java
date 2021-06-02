package model.factory;

import model.good.Egg;
import model.good.Flour;

public class Mill extends Factory<Egg, Flour> {

    public Mill() {
        super(150, 4);
    }
}
