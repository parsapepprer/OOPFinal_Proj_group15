package model.animal.collector;

public class Cat extends Collector {
    private static int NUMBER_OF_CATS = 0;

    public Cat() {
        super(150, ++NUMBER_OF_CATS);
    }
}
