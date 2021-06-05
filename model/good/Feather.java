package model.good;

public class Feather extends Good{
    private static int NUMBER_OF_FEATHERS = 0;

    public Feather() {
        super(1, 4, "Feather" + ++NUMBER_OF_FEATHERS, 20);
    }
}
