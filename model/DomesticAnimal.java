package model;

import java.util.Random;

public abstract class DomesticAnimal <T>{ // chicken    turkey   buffalo
    protected int price;
    protected String product;
    protected int produceTime;
    protected int lifetime;
    protected int x, y;
    protected Random rand ;

    public DomesticAnimal(int price, String product, int produceTime) {
        this.lifetime = 100;
        this.price = price;
        this.product = product;
        this.produceTime = produceTime;
        this.rand= new Random();
        this.x = rand.nextInt(6);
        this.y = rand.nextInt(6);
    }

    public void moveRandom() {
        int vertical = rand.nextInt(2);
        int direction = rand.nextInt(2);
        if (vertical == 0) {
            if (x == 0) {
                x += 1;
            } else if (x == 5) {
                x -= 1;
            } else {
                if (direction == 0) {
                    x += 1;
                } else {
                    x -= 1;
                }
            }
        } else {
            if (y == 0) {
                y += 1;
            } else if (y == 5) {
                y -= 1;
            } else {
                if (direction == 0) {
                    y += 1;
                } else {
                    y -= 1;
                }
            }

        }
    }//

    public void reduceLife(){
        this.lifetime = this.lifetime * 9/10 ;
    }

    public void eatGrass(){this.lifetime = 100 ;}

}
