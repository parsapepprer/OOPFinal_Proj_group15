package model.factory;

public abstract class Factory<K,V> {
    protected int price;
    protected int produceTime;
    protected String product;

    public Factory(int price, int produceTime) {
        this.price = price;
        this.produceTime = produceTime;
    }

    public void produce(){

    }
}
