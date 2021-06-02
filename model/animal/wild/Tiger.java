package model.animal.wild;

public class Tiger extends Wild {
    private static int NUMBER_OF_TIGERS = 0;

    public Tiger() {
        super(4, 500, ++NUMBER_OF_TIGERS);
    }
}