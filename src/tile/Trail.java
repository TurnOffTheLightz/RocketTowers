package tile;

import classes.Game;
import classes.Handler;
import graphics.Sprite;
import states.Id;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Trail extends Tile{

    public float alpha=1.0f;

    public BufferedImage img;
    private int startx,starty;

    public Trail(int x, int y, int width, int height, Id id, Handler handler, int whichSide,BufferedImage img) {
        super(x, y, width, height, id, handler, whichSide);
        this.startx=x;
        this.starty=y;
        this.img=img;
    }

    public void render(Graphics g) {
            Graphics2D g2 = (Graphics2D)g;

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha));
            g.drawImage(img,x,y,width,height,null);
    }

    public void tick() {
        alpha-=0.09;
        if(alpha<0.09)die();
    }
}
