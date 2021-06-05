package model.good;

public class IceCream extends Good{
    private static int NUMBER_OF_ICE_CREAMS = 0;

    public IceCream() {
        super(4, 6, "IceCream" + ++ NUMBER_OF_ICE_CREAMS, 120);
    }
}
