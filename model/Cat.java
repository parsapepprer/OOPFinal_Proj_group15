package model;

import java.util.Random;

public class Cat {
    int price = 150;
    int x , y;
    Random rand = new Random();

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

}
