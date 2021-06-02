package model.animal.domestic;

import model.animal.Animal;

public abstract class Domestic<T> extends Animal {
    protected int produceTime;
    protected int lifetime;

    public Domestic(int price, int produceTime, int number) {
        super(price, number);
        this.lifetime = 100;
        this.produceTime = produceTime;
    }



    public void reduceLife(){
        this.lifetime = this.lifetime * 9/10 ;
    }

    public void eatGrass(){this.lifetime = 100 ;}

}
