package model.good;

public class Flour extends Good{
    private static int NUMBER_OF_FLOURS = 0;

    public Flour() {
        super(2, 5, "Flour" + ++NUMBER_OF_FLOURS, 40);
    }
}
