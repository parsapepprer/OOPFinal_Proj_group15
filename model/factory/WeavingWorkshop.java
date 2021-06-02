package model.factory;

import model.good.Fabric;
import model.good.Feather;

public class WeavingWorkshop extends Factory<Feather, Fabric> {
    public WeavingWorkshop() {
        super(250, 5);
    }
}
