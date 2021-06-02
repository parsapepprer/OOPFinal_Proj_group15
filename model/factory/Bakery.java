package model.factory;

import model.good.Bread;
import model.good.Flour;

public class Bakery extends Factory<Flour, Bread> {
    public Bakery() {
        super(250, 5);
    }
}
