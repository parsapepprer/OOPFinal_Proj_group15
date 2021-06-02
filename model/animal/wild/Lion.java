package model.animal.wild;

public class Lion extends Wild {
    private static int NUMBER_OF_LIONS = 0;

    public Lion() {
        super(3, 300, ++NUMBER_OF_LIONS);
    }

}
