package entity.bullets;

import classes.Game;
import classes.Handler;
import entity.Entity;
import graphics.Sprite;
import states.AmmoBoostType;
import states.AmmoType;
import states.Id;
import tile.Trail;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.util.Arrays;

public abstract class Ammo extends Entity {

    public AmmoType ammoType;
    public AmmoBoostType ammoBoostType;
    public boolean active=false;
    public int frames=0,ticks=0;
    public double angle;

    public Sprite sprite;
    public Sprite []particle;

    public BufferedImage srcImage;
    public BufferedImage dstImage;


    public Ammo(double x, double y, int width, int height, Id id, Handler handler,int whichSide) {
        super(x, y, width, height, id, handler);
    }
    public Ammo(int width, int height, Id id, Handler handler,int whichSide,AmmoBoostType abt) {
        super(width, height, id, handler);
        this.ammoBoostType=abt;
    }

    public abstract void render(Graphics g);
    public abstract void tick();
    public abstract int getAmmunition();
    public abstract void setAmmunition(int ammunition);
    public abstract void decreaseAmmunition();

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean t) {
        active = t;
    }
    public AmmoType getAmmoType() {
        return ammoType;
    }
    public static BufferedImage asBufferedImage(int[] pixels, int width, int height) {
        ColorModel cm = ColorModel.getRGBdefault();
        int[] bandMasks = new int[] { 0x00ff0000, 0x0000ff00, 0x000000ff, 0xff000000 };
        DataBuffer dataBuffer = new DataBufferInt(pixels, pixels.length);
        WritableRaster raster = Raster.createPackedRaster(
                dataBuffer,
                width,
                height,
                width,
                bandMasks,
                null);
        return new BufferedImage(cm, raster, false, null);
    }
    public void rotate(int[]pixels){
        srcImage = asBufferedImage(pixels.clone(), width, height);
        dstImage = asBufferedImage(pixels, width, height);
        Arrays.fill(pixels, 0x00000000);


        Graphics2D g2 = dstImage.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.setComposite(AlphaComposite.Src);
        g2.transform(AffineTransform.getRotateInstance(-45 * (Math.PI / 180), x/2,y/2));
        g2.drawImage(srcImage, null,0,0);
        g2.dispose();
    }
    public int[]loadImage(BufferedImage img){
        if (img.getType() != BufferedImage.TYPE_INT_ARGB) {
            BufferedImage tmp = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
            tmp.getGraphics().drawImage(img, 0, 0, null);
            img = tmp;
        }
        return ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
    }

}
