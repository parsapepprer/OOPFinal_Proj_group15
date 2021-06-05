package model.good;

public class Bread extends Good{
    private static int NUMBER_OF_BREADS = 0;

    public Bread() {
        super(4, 6, "Bread" + ++NUMBER_OF_BREADS, 80);
    }
}
