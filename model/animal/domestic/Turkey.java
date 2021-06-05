package model.animal.domestic;

public class Turkey extends Domestic {
    private static int NUMBER_OF_TURKEYS = 0;

    public Turkey() {
        super(200, 3, "Turkey" + ++NUMBER_OF_TURKEYS, "Feather");
    }
}