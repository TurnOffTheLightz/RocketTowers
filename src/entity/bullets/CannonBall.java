package entity.bullets;

import classes.Game;
import classes.Handler;
import classes.Player;
import entity.weapons.Weapon;
import graphics.Sprite;
import states.AmmoBoostType;
import states.AmmoType;
import states.Id;
import tile.Trail;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CannonBall extends Ammo {
    private int ammunition = 10000;
    private BufferedImage img;

    public CannonBall(double x, double y, int width, int height, Id id, Handler handler,int whichSide,AmmoBoostType abt) {
        super(x, y, width, height, id, handler,whichSide);
        this.ammoBoostType=abt;
        this.ammoType = AmmoType.classic;
        this.whichSide=whichSide;
        particle = new Sprite[8];
        if(ammoBoostType== AmmoBoostType.classic){
            this.sprite= new Sprite(3,2,Game.sheet);
            for(int i=0;i<particle.length;i++){
                particle[i]=new Sprite(3,2, Game.sheet);
            }
        }else if(ammoBoostType== AmmoBoostType.classicFrozen){
            this.ammunition=9000;
            this.sprite=new Sprite(2,3,Game.sheet);
            for(int i=0;i<particle.length;i++){
                particle[i]=new Sprite(2,3, Game.sheet);
            }
        }else if(ammoBoostType== AmmoBoostType.classicFire){
            this.ammunition=8000;
            this.sprite = new Sprite(1,3,Game.sheet);
            for(int i=0;i<particle.length;i++){
                particle[i]=new Sprite(1,3, Game.sheet);
            }
        }
        this.img = this.sprite.getBufferedImage();
    }
    public CannonBall(int width, int height, Id id, Handler handler,int whichSide,AmmoBoostType abt) {
        super(width, height, id, handler,whichSide,abt);
        this.ammoType = AmmoType.classic;
        this.ammoBoostType=abt;
        this.whichSide=whichSide;
        particle = new Sprite[8];
        if(ammoBoostType== AmmoBoostType.classic){
            this.sprite= new Sprite(3,2,Game.sheet);
            for(int i=0;i<particle.length;i++){
                particle[i]=new Sprite(3,2, Game.sheet);
            }
        }else if(ammoBoostType== AmmoBoostType.classicFrozen){
            this.ammunition=9000;
            this.sprite=new Sprite(2,3,Game.sheet);
            for(int i=0;i<particle.length;i++){
                particle[i]=new Sprite(2,3, Game.sheet);
            }
        }else if(ammoBoostType== AmmoBoostType.classicFire){
            this.ammunition=8000;
            this.sprite = new Sprite(1,3,Game.sheet);
            for(int i=0;i<particle.length;i++){
                particle[i]=new Sprite(1,3, Game.sheet);
            }
        }
    }

    public void render(Graphics g) {
        if(active){
            g.drawImage(img,(int)x,(int)y,width,height,null);
        }
    }

    public void tick() {
        if(active) {
            x += velx;
            y += vely;
            setRotateAngle();
            if(Game.frames%20==0) this.frames++;
            if(frames>3) frames=0;
            ticks++;
            if(ticks>10)
                handler.addTile(new Trail((int)x,(int)y,width,height,id.trail,handler,whichSide,particle[frames].getBufferedImage()));

        }
    }
    private void setRotateAngle(){
        if(whichSide==0){
            this.angle = Math.atan2(vely,velx);
        }else if(whichSide==1){
            this.angle = Math.atan2(velx,vely)+Math.PI/2;
        }
    }

    public void die(){
        handler.removeAmmunition(this);
    }

    public void decreaseAmmunition(){
        this.ammunition--;
    }
    public int getAmmunition() {
        return ammunition;
    }

    public void setAmmunition(int ammunition) {
        this.ammunition = ammunition;
    }
    public void setAmmoBoostType(AmmoBoostType abt){
        this.ammoBoostType=abt;
    }
}
