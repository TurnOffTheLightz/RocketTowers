package entity.bullets;

import classes.Game;
import classes.Handler;
import graphics.Sprite;
import states.AmmoBoostType;
import states.AmmoType;
import states.Id;
import tile.Trail;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class R_1 extends Ammo {

    private int ammunition = 70;
    private BufferedImage img;
    public int frames=0;
    public Sprite []particle;
    private int[]pixels;

    public R_1(double x, double y, int width, int height, Id id, Handler handler, int whichSide,AmmoBoostType abt) {
        super(x, y, width, height, id, handler, whichSide);
        this.ammoType = AmmoType.r_1;
        this.ammoBoostType=abt;
        this.whichSide=whichSide;
        particle = new Sprite[8];
        if(whichSide==0){
            this.sprite = new Sprite(13,1,64,22, Game.sheet);
            for(int i=0;i<particle.length;i++){
                particle[i]=new Sprite(13,1,64,22, Game.sheet);
            }
        }else if(whichSide==1){
            this.sprite = new Sprite(13,2,64,22, Game.sheet);
            for(int i=0;i<particle.length;i++){
                particle[i]=new Sprite(13,2,64,22, Game.sheet);
            }
        }
//        this.img = this.sprite.getBufferedImage();
        try {
            this.img = ImageIO.read(getClass().getResource("/pociskkurwa.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.width=img.getWidth();
        this.height=img.getHeight();
        pixels = loadImage(img);
        rotate(pixels);
    }
    public R_1(int width, int height, Id id, Handler handler, int whichSide, AmmoBoostType abt) {
        super(width, height, id, handler, whichSide,abt);
        this.ammoType = AmmoType.r_1;
        if(whichSide==0){
            this.sprite = new Sprite(13,1,64,22, Game.sheet);
        }else if(whichSide==1){
            this.sprite = new Sprite(13,2,64,22, Game.sheet);
        }
    }

    public void render(Graphics g) {
        if(active){
            g.drawImage(img,(int)x,(int)y,64,64,null);
        }
    }


    public void tick() {
        if(active){
            x+=velx;
            y+=vely;
//            setRotateAngle();
//            rotate(pixels);
            if(Game.frames%20==0) this.frames++;
            if(frames>3) frames=3;
            handler.addTile(new Trail((int)x,(int)y,width,height,id.trail,handler,whichSide,img));
        }
    }

    private void setRotateAngle(){
        if(whichSide==0){
            this.angle = Math.atan2(vely,velx);
        }else if(whichSide==1){
            this.angle = Math.atan2(velx,vely)+Math.PI/2;
        }
    }
    public int getAmmunition() {
        return ammunition;
    }

    public void setAmmunition(int ammunition) {
        this.ammunition=ammunition;
    }

    public void decreaseAmmunition() {
        this.ammunition--;
    }
    public void die(){
        handler.removeAmmunition(this);
    }
    public Rectangle getBounds(){ //depending on angle, shape changes
        return new Rectangle((int)x,(int)y+21,width,height-41);
    }
}
