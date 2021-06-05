package model.animal;

import model.Game;
import model.animal.protective.Protective;

import java.util.Random;

public abstract class Animal {
    protected Random rand;
    protected String name;
    protected int i, j;
    protected int preI, preJ;
    protected int price;
    protected int space;
    protected int lifetime;
    protected int step;

    public Animal(int price, String name, int lifetime, int space, int step) {
        this.rand = new Random();
        this.i = rand.nextInt(Game.SIZE);
        this.j = rand.nextInt(Game.SIZE);
        this.preI = -1;
        this.preJ = -1;
        this.price = price;
        this.lifetime = lifetime;
        this.space = space;
        this.name = name;
        this.step = step;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public int getPreI() {
        return preI;
    }

    public int getPreJ() {
        return preJ;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getSpace() {
        return space;
    }

    @Override
    public String toString() {
        return name + " [" + (i + 1) + " " + (j + 1) + "]";
    }

    public void move(boolean vertical, boolean direction) {
        if (vertical) {
            if (i < step) {
                i += step;
            } else if (i >= Game.SIZE - step) {
                i -= step;
            } else {
                i += (direction ? step : -step);
            }
        } else {
            if (j < step) {
                j += step;
            } else if (j >= Game.SIZE - step) {
                j -= step;
            } else {
                j += (direction ? step : -step);
            }
        }
    }

    public boolean intersection(Animal that) {
        if (this.i != this.preI && this.j != this.preJ) return false;
        if (that.j != that.preJ && that.i != that.preI) return false;
        if (this.i == this.preI && that.j == that.preJ) return false;
        if (this.j == this.preJ && that.i == that.preI) return false;
        if (this.i == this.preI) {
            if ((this.j >= that.j && this.preJ <= that.j && this.j <= that.preJ) || (that.j >= this.j && that.preJ <= this.j && that.j <= this.preJ)) return true;
            if ((this.j <= that.j && this.preJ <= that.j && this.j >= that.preJ && this.preJ >= that.preJ) || (that.j <= this.j && that.preJ <= this.j && that.j >= this.preJ && that.preJ >= this.preJ)) return true;
            if ((this.j <= that.preJ && this.preJ <= that.preJ && this.j >= that.j && this.preJ >= that.j) || (that.j <= this.preJ && that.preJ <= this.preJ && that.j >= this.j && that.preJ >= this.j)) return true;
        }
        if (this.j == this.preJ) {
            if ((this.i >= that.i && this.preI <= that.i && this.i <= that.preI) || (that.i >= this.i && that.preI <= this.i && that.i <= this.preI)) return true;
            if ((this.i <= that.i && this.preI <= that.i && this.i >= that.preI && this.preI >= that.preI) || (that.i <= this.i && that.preI <= this.i && that.i >= this.preI && that.preI >= this.preI)) return true;
            if ((this.i <= that.preI && this.preI <= that.preI && this.i >= that.i && this.preI >= that.i) || (that.i <= this.preI && that.preI <= this.preI && that.i >= this.i && that.preI >= this.i)) return true;
        }
        return false;
    }
}
