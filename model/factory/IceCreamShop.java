package model.factory;

import model.good.IceCream;
import model.good.PacketMilk;

public class IceCreamShop extends Factory<PacketMilk, IceCream> {
    public IceCreamShop() {
        super(550, 7);
    }
}
