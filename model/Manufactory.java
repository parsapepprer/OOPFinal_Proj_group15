package model;

public abstract class Manufactory<T> {
    protected int price;
    protected int produceTime;
    String product;

    public Manufactory(int price, int produceTime, String product) {
        this.price = price;
        this.produceTime = produceTime;
        this.product = product;
    }

    public void produce(){

    }
}
