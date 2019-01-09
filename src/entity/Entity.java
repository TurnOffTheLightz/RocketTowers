package entity;

import classes.Handler;
import org.w3c.dom.css.Rect;
import states.Id;
import graphics.Sprite;

import java.awt.*;

public abstract class Entity{
    /////
    public static final double GRAVITY = 5.0;
    public static final double GRAVITY_POWER = 0.05;

    public double x,y;
    public int width,height;
    protected double velx,vely;
    public Id id;
    public Handler handler;
    public Sprite sprite;
    public double gravity=0.0;
    public int whichSide;

    public Entity(double x,double y,int width,int height,Id id,Handler handler){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.id=id;
        this.handler=handler;
    }
    public Entity(int width,int height,Id id,Handler handler){
        this.width=width;
        this.height=height;
        this.id=id;
        this.handler=handler;
    }
    public abstract void render(Graphics g);
    public abstract void tick();

    public Rectangle getBounds(){
        return new Rectangle((int)x,(int)y,width,height);
    }

    public void die(){
        handler.removeEntity(this);
    }

    public Id getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getVelx() {
        return velx;
    }

    public void setVelx(double velx) {
        this.velx = velx;
    }

    public double getVely() {
        return vely;
    }

    public void setVely(double vely) {
        this.vely = vely;
    }
}
