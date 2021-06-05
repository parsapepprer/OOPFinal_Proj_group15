package model.good;

public class Shirt extends Good{
    private static int NUMBER_OF_SHIRTS = 0;
    public Shirt() {
        super(4, 6, "Shirt" + ++NUMBER_OF_SHIRTS, 100);
    }
}
