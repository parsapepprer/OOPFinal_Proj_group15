package model.animal.protective;

public class Hound extends Protective {
    private static int NUMBER_OF_HOUNDS = 0;

    public Hound() {
        super(100, ++NUMBER_OF_HOUNDS);
    }
}
