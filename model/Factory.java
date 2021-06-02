package model;

public abstract class Factory<T> {
    protected int price;
    protected int produceTime;
    String product;

    public Factory(int price, int produceTime, String product) {
        this.price = price;
        this.produceTime = produceTime;
        this.product = product;
    }

    public void produce(){

    }
}
