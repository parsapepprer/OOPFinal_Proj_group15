package model.animal.wild;

public class Bear extends Wild {
    private static int NUMBER_OF_BEARS = 0;

    public Bear(){
        super(4, 400, ++NUMBER_OF_BEARS);
    }
}
