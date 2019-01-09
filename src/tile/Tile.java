package tile;

import classes.Handler;
import graphics.Sprite;
import graphics.SpriteSheet;
import states.Id;

import java.awt.*;

public abstract class Tile{
    public int x,y,width,height;
    public Id id;
    public boolean hitable=false;
    public Handler handler;
    public Sprite sprite;
    public int whichSide;
    public boolean isTowerPiece=false;

    public Tile(int x, int y, int width, int height, Id id, Handler handler,int whichSide){ //side tiles
        this.whichSide=whichSide;
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.id=id;
        this.handler=handler;
    }

    public Tile(int x, int y, int width, int height, Id id, Handler handler) {//non side Tiles
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.id=id;
        this.handler=handler;
    }

    public abstract void render(Graphics g);
    public abstract void tick();

    public void die(){
        handler.removeTile(this);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public Rectangle getBounds(){
        return new Rectangle(x,y,width,height);
    }
    public Rectangle getBoundsTop(){
        return new Rectangle(getX()+5,getY(),width-10,5);
    }
    public Rectangle getBoundsBottom(){
        return new Rectangle(getX()+5,getY()+height,width-10,5);
    }
    public Rectangle getBoundsLeft(){
        return new Rectangle(getX(),getY()+5,5,height-5);
    }
    public Rectangle getBoundsRight(){
        return new Rectangle(getX()+width-5,getY()+5,5,height-5);
    }

}


