package model.good;

public class Egg extends Good{
    private static int NUMBER_OF_EGGS = 0;

    public Egg() {
        super(1, 4, "Egg" + ++NUMBER_OF_EGGS, 15);
    }
}
