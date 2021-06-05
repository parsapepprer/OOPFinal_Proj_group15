package model.good;

public class Milk extends Good{
    private static int NUMBER_OF_MILKS = 0;

    public Milk() {
        super(1, 4, "Milk" + ++NUMBER_OF_MILKS, 25);
    }
}
