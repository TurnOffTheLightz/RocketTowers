package classes.HUD;

import classes.Game;
import classes.Handler;
import classes.Player;
import graphics.Sprite;
import states.AmmoBoostType;
import states.AmmoType;
import states.GameState;
import states.WeaponType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class HUDAmmoObject {

    public boolean isHighlited=false;

    private int x,y,width,height;
    public Sprite ammoSprite;
    HUD hud;
    public boolean active = false;

    public AmmoType ammoType;

    private BufferedImage hilite64pxImg;
    public static WeaponType weaponType=WeaponType.cannon;
    public AmmoBoostType ammoBoostType;

    public HUDAmmoObject(HUD hud, AmmoType ammoType, AmmoBoostType ammoBoostType){
        try {
            hilite64pxImg = ImageIO.read(getClass().getResource("/64pxhilite.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.hud=hud;
        this.width=64;
        this.height=64;
        this.ammoType = ammoType;
        this.ammoBoostType=ammoBoostType;
        if(ammoType==AmmoType.classic){
            this.x=hud.nextAmmoX();
            this.y=hud.hudy;
            if(ammoBoostType==AmmoBoostType.classic) ammoSprite = new Sprite(3,2, Game.sheet);
            if(ammoBoostType==AmmoBoostType.classicFire) ammoSprite = new Sprite(1,3, Game.sheet);
            if(ammoBoostType==AmmoBoostType.classicFrozen) ammoSprite = new Sprite(2,3, Game.sheet);
        }else if(ammoType==AmmoType.r_1){
            this.height=44;
            if(ammoBoostType==AmmoBoostType.r_1) ammoSprite = new Sprite(13,1,64,32,Game.sheet);// ROCKET SPRITE
        }else if(ammoType==AmmoType.rocket){
            ammoSprite = new Sprite(3,1, Game.sheet);// ROCKET SPRITE
        }else if(ammoType == AmmoType.none){
            //none
        }
    }
    public void render(Graphics g){
        if(isHighlited){
            g.drawImage(hilite64pxImg,x,y,64,64,null);
        }
        if(active){
            if(weaponType==WeaponType.tankCannon){
                g.drawImage(ammoSprite.getBufferedImage(),x,y+15,width,height,null);
            }else{
                g.drawImage(ammoSprite.getBufferedImage(),x,y,width,height,null);
            }
        }
    }
    public void tick(){

    }
    public Rectangle getBounds(){
        return new Rectangle(this.x,this.y,this.width,this.height);
    }
    public AmmoType getAmmoType() {
        return ammoType;
    }

    public void setAmmoType(AmmoType ammoType) { this.ammoType = ammoType; }

    public void setHighlited(boolean highlited) { isHighlited = highlited;
    }

    public void setY(int y){
        this.y=y;
    }
    public int getY(){
        return y;
    }
    public void setXY(int x,int y){
        this.x=x;
        this.y=y;
    }
}
