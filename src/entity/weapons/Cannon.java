package entity.weapons;

import classes.Game;
import classes.Handler;
import entity.Entity;
import entity.bullets.Ammo;
import input.MouseInput;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import states.AmmoType;
import states.Id;
import states.WeaponType;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.util.LinkedList;

public class Cannon extends Weapon {

    private int barrelX,barrelY;
    public LinkedList<Ammo> ammoList = new LinkedList<>();
    private int[] sourcePixels;
    public AmmoType ammoType;
    private BufferedImage cannonImg;

    public Cannon(int x, int y, int width, int height, Id id, Handler handler,int whichSide) {//x,y ->2,14 |*32 || x,y ->44,14 |*32
        super(x, y, width, height, id, handler,whichSide);
        this.weaponType = WeaponType.cannon;
        try {
            if (whichSide == 0) {
                cannonImg = ImageIO.read(getClass().getResource("/cannon-0.png"));
            } else if (whichSide == 1) {
                cannonImg = ImageIO.read(getClass().getResource("/cannon-1.png"));
            }
        }catch(Exception e){
            System.out.println("wrong path for cannons");
        }
    }

    public void render(Graphics g) {
        if(isActive) g.drawImage(cannonImg,drawXY.x,drawXY.y,width,height,null);
    }

    public void tick() {
    }

    public void setAmmoType(AmmoType ammoType) {
        this.ammoType = ammoType;
    }

    public int getBarrelX(){
        return barrelX;
    }
    public int getBarrelY(){
        return barrelY;
    }
    public void setBarrelX(int x){

    }
    public void setBarrelY(int y) {

    }
    private static int[] convertTo2DUsingGetRGB(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int[] result = new int[width*height];

        int k=0;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                result[k] = image.getRGB(col, row);
                k++;
            }
        }
        return result;
    }

}
