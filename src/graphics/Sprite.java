package graphics;

import classes.Game;

import java.awt.*;
import java.awt.image.*;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;

public class Sprite {
    private BufferedImage image;
    private int width=32,height=32;
    public int offsetX,offsetY;
    public int[] pixels;

    public Sprite(int x, int y,SpriteSheet sheet){
        this.image = sheet.getImage(x,y);
    }
    public Sprite(int x, int y,int width, int height, SpriteSheet sheet){
        this.image=sheet.getImage(x, y, width, height);
        this.width=width;
        this.height=height;
    }
    public Sprite(int x, int y,int width, int height, int offsetX,int offsetY,SpriteSheet sheet){
        this.image=sheet.getImage(x, y, width, height,offsetX,offsetY);
        this.width=width;
        this.height=height;
        this.offsetX=offsetX;
        this.offsetY=offsetY;
    }
    public BufferedImage getBufferedImage(){
        return this.image;
    }
    public void setBufferedImage(BufferedImage image){
        this.image = image;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}


