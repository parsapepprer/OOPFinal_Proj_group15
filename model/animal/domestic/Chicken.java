package model.animal.domestic;

public class Chicken extends Domestic {
    private static int NUMBER_OF_CHICKENS = 0;

    public Chicken() {
        super(100, 2, "Chicken" + ++NUMBER_OF_CHICKENS, "Egg");
    }
}
