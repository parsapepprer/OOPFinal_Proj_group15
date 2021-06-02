package model.factory;

import model.good.Fabric;
import model.good.Shirt;

public class SewingWorkshop extends Factory<Fabric, Shirt> {
    public SewingWorkshop() {
        super(400, 6);
    }
}
