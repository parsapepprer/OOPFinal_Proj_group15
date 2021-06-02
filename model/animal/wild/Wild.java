package model.animal.wild;

import model.animal.Animal;

public abstract class Wild extends Animal { // lion  bear tiger

    protected int cageTime;

    public Wild(int cageTime, int price, int number) {
        super(price, number);
        this.cageTime = cageTime;
    }

//    public void cage(){ }
}
