package tile.tower.tower_components;

import classes.Handler;
import graphics.Sprite;
import states.Id;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Atom extends TowerPiece{
    private BufferedImage atomImage;
    private int xx,yy,x,y; //inside the block

    public Atom(int x,int y, int xx, int yy, int width, int height, Id id, Handler handler, int whichSide, Sprite sprite) {
        super(x, y, width, height, id, handler, whichSide);
        this.xx=xx;
        this.yy=yy;
        this.x=x;
        this.y=y;
        System.out.println("xx->\t"+(xx)+"\tyy->\t"+(yy)+"\t\tx->\t"+x+"\ty->\t"+y);
//        this.atomImage = sprite.getBufferedImage();
//        this.atomImage = this.sprite.getBufferedImage().getSubimage(x,y,width,height);
    }
    public void render(Graphics g) {g.drawImage(atomImage,xx,yy,width,height,null);}

    public void tick() {

    }
    public void die(){
       removeAtom(this);
    }
}
