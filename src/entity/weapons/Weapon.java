package entity.weapons;

import classes.Handler;
import entity.Entity;
import states.Id;
import states.WeaponType;

import java.awt.*;

public abstract class Weapon extends Entity {

    public boolean isActive = false;
    public WeaponType weaponType;
    public Point drawXY;

    public Weapon(double x, double y, int width, int height, Id id, Handler handler, int whichSide) {
        super(x, y, width, height, id, handler);
        this.whichSide=whichSide;
        this.drawXY=new Point((int)x,(int)y);
    }

    public abstract void render(Graphics g);

    public abstract void tick();

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getWhichSide() {
        return whichSide;
    }
}
