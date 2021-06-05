package model.animal.domestic;

public class Buffalo extends Domestic {
    private static int NUMBER_OF_BUFFALOES = 0;

    public Buffalo() {
        super(400, 5, "Buffalo" + ++NUMBER_OF_BUFFALOES, "Milk");
    }
}