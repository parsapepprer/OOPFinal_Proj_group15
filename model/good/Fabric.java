package model.good;

public class Fabric extends Good{
    private static int NUMBER_OF_FABRICS = 0;

    public Fabric() {
        super(2, 5, "Fabric" + ++NUMBER_OF_FABRICS, 50);
    }
}
