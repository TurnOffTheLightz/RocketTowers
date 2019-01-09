package entity.bullets;

import classes.Game;
import classes.Handler;
import graphics.Sprite;
import states.AmmoBoostType;
import states.AmmoType;
import states.Id;
import tile.Trail;

import java.awt.*;

public class Rocket extends Ammo {

    private int ammunition=9;
    public int frames=0;
    public Sprite []particle;

    public Rocket(double x, double y, int width, int height, Id id, Handler handler,int whichSide, AmmoBoostType abt) {
        super(x, y, width, height, id, handler,whichSide);
        this.ammoType = AmmoType.rocket;
        this.ammoBoostType=abt;
        this.sprite = new Sprite(3,1, Game.sheet);
        particle = new Sprite[8];
        for(int i=0;i<particle.length;i++){
            particle[i]=new Sprite(3,2, Game.sheet);
        }
    }
    public Rocket(int width, int height, Id id, Handler handler, int whichSide, AmmoBoostType abt) {
        super(width, height, id, handler,whichSide,abt);
        this.ammoType = AmmoType.rocket;
        this.sprite = new Sprite(3,1, Game.sheet);
    }

    public void render(Graphics g) {
//        if(active) g.drawImage(sprite.getBufferedImage(),(int)x,(int)y,width,height,null);
    }

    public void tick() {
        if(active) {
            x += velx;
            y += vely;
            setRotateAngle();
            if(Game.frames%20==0) this.frames++;
            if(frames>3) frames=3;
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

}
