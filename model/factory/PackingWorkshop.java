package model.factory;

import model.good.Milk;
import model.good.PacketMilk;

public class PackingWorkshop extends Factory<Milk, PacketMilk> {
    public PackingWorkshop() {
        super(400, 6);
    }
}
