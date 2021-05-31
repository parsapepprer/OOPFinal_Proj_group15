package model;

import java.util.Random;

public abstract class WildAnimal { // lion  bear tiger
    protected int x, y;
    protected Random rand;
    protected int cageTime;

    public WildAnimal(int cageTime) {
        this.rand = new Random();
        this.x = rand.nextInt(6);
        this.y = rand.nextInt(6);
        this.cageTime = cageTime;
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
    }

//    public void cage(){ }
}
