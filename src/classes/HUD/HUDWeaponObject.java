package classes.HUD;

import classes.Game;
import graphics.Sprite;
import states.AmmoType;
import states.WeaponType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class HUDWeaponObject {
    public boolean isHighlited=false;
    public ArrayList<HUDAmmoObject> cannonBalls = new ArrayList<>();
    public ArrayList<HUDAmmoObject> tankCannonBalls = new ArrayList<>();


    private int x,y,width,height;
    private BufferedImage weaponImg;
    HUD hud;
    private BufferedImage hilite96pxImg;

    public WeaponType weaponType;

    public HUDWeaponObject(HUD hud, WeaponType weaponType){
        try {
            hilite96pxImg =ImageIO.read(getClass().getResource("/96pxhilite.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.hud=hud;
        this.x=hud.nextWeaponX();
        this.y=hud.hudy;
        this.width=90;
        this.height=30;
        this.weaponType = weaponType;
        try {
            if (weaponType == WeaponType.cannon) {
                weaponImg = ImageIO.read(getClass().getResource("/cannon-0.png"));
            } else if (weaponType == WeaponType.tankCannon) {
                weaponImg = ImageIO.read(getClass().getResource("/r-1-0.png"));
            }else if(weaponType == WeaponType.none){
                weaponImg = ImageIO.read(getClass().getResource("/noneWeapon.png")); // nonWeapon
            }
        }catch (IOException e){
            System.out.println("HUDWeaponObject constructor ERROR");
        }
    }
    public void render(Graphics g){
        if(isHighlited){
            g.drawImage(hilite96pxImg,x,y,96,32,null);
        }
        g.drawImage(weaponImg,x,y,width,height,null);
    }
    public void tick(){

    }
    public void setY(int y){
        this.y=y;
    }
    public int getY(){
        return y;
    }
    public Rectangle getBounds(){
        return new Rectangle(this.x,this.y,this.width,this.height);
    }
    public boolean isHighlited() {
        return isHighlited;
    }

    public void setHighlited(boolean highlited) {
        isHighlited = highlited;
    }
    public void setWeaponType(WeaponType wt){
        this.weaponType = weaponType;
    }
}
