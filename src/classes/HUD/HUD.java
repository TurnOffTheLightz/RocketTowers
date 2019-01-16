package classes.HUD;

import classes.Game;
import classes.Handler;
import classes.Player;
import entity.weapons.Weapon;
import graphics.Sprite;
import input.MouseInput;
import states.AmmoBoostType;
import states.AmmoType;
import states.GameState;
import states.WeaponType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class HUD {
    private boolean isOpened=false;
    private boolean isClosing=false;

    private int x,y,width,height;
    private Sprite ammoHudSprite;                                           //current ammo img
//    public ArrayList<HUDAmmoObject> hudAmmoObjects = new ArrayList<>();
    public ArrayList<HUDWeaponObject> hudWeaponObjects = new ArrayList<>();
    public ArrayList<HUDAmmoObject> cannonBalls = new ArrayList<>();
    public ArrayList<HUDAmmoObject> tankCannonBalls = new ArrayList<>();
    private BufferedImage arrowImg;
    private Point arrowXY;
    public boolean choosingAmmo = false;
    public boolean currentHud = false;
    public boolean weaponChanged = false;
    private int whichSide;
    public int hudy=Game.HEIGHT*Game.SCALE;

    private WeaponType weaponType;

    private BufferedImage hudImg;
    public HUD(int x, int y, int whichSide){
        this.x=x;
        this.y=y;
        arrowXY=new Point(x+125,hudy);
        this.whichSide=whichSide;
        this.width = 634;//125+25+96+25+96+25+96+25+96+25
        this.height = 216;//30+32+60+64+30

        addWeaponHudObject(new HUDWeaponObject(this, WeaponType.cannon));
        addWeaponHudObject(new HUDWeaponObject(this, WeaponType.tankCannon));

        addCannonBall(new HUDAmmoObject(this,AmmoType.classic, AmmoBoostType.classic));
        addCannonBall(new HUDAmmoObject(this,AmmoType.classic,AmmoBoostType.classicFrozen));
        addCannonBall(new HUDAmmoObject(this,AmmoType.classic,AmmoBoostType.classicFire));

        addTankCannonBalls(new HUDAmmoObject(this,AmmoType.r_1,AmmoBoostType.r_1));
        addTankCannonBalls(new HUDAmmoObject(this,AmmoType.r_1,AmmoBoostType.r_1));
        addTankCannonBalls(new HUDAmmoObject(this,AmmoType.r_1,AmmoBoostType.r_1));


        ammoHudSprite = new Sprite(3,2,Game.sheet);
        if(whichSide==0){
            arrowImg = new Sprite(12,2,32,11,0,0,Game.sheet).getBufferedImage();
        }else if(whichSide==1){
            arrowImg = new Sprite(12,2,32,11,0,10,Game.sheet).getBufferedImage();
        }

        try {
            hudImg = ImageIO.read(getClass().getResource("/ammoHud.png"));
        } catch (IOException e) {
            System.out.println("chuj EROR hud constructor");
        }
    }
    public void render(Graphics g){
        if(whichSide==0){
            g.drawImage(ammoHudSprite.getBufferedImage(),100,30,ammoHudSprite.getWidth(),ammoHudSprite.getHeight(),null);
            g.setColor(Color.white);
            g.setFont(new Font("Courier",Font.ITALIC,30));
            g.drawString("x"+Integer.toString(Handler.playerleft.getCurrentAmmo()),169,69);

            if(Game.gameState== GameState.leftMoves){
                g.setColor(Color.red);
                g.setFont(new Font("Courier",Font.HANGING_BASELINE,44));
                g.drawString("PLAYER 1 TURN",50,150);

                g.setColor(Color.white);
                g.setFont(new Font("Arial",Font.ITALIC,16));
                g.drawString("("+Double.toString(MouseInput.mouseX)+","+Double.toString(MouseInput.mouseY)+")",15,15);
                g.drawString("angle->\t"+Double.toString(-1*Math.toDegrees(Handler.playerleft.angle)),15,45);

            }
        }else if(whichSide==1){
            g.drawImage(ammoHudSprite.getBufferedImage(),1300,30,ammoHudSprite.getWidth(),ammoHudSprite.getHeight(),null);
            g.setColor(Color.white);
            g.setFont(new Font("Courier",Font.ITALIC,30));
            g.drawString("x"+Integer.toString(Handler.playerright.getCurrentAmmo()),1369,69);

            if(Game.gameState==GameState.rightMoves){
                g.setColor(Color.red);
                g.setFont(new Font("Courier",Font.HANGING_BASELINE,44));
                g.drawString("PLAYER 2 TURN",777,150);

                g.setColor(Color.white);
                g.setFont(new Font("Arial",Font.ITALIC,16));
                g.drawString("("+Double.toString(MouseInput.mouseX)+","+Double.toString(MouseInput.mouseY)+")",15,15);
                g.drawString("angle->\t"+Double.toString(Math.toDegrees(Handler.playerright.angle)),15,45);
            }
        }
        if(isOpened){

            g.drawImage(hudImg,x,hudy,width,height,null);

            g.drawImage(arrowImg,arrowXY.x,arrowXY.y,arrowImg.getWidth(),arrowImg.getHeight()*2,null);

            for(HUDWeaponObject hwo:hudWeaponObjects){
                hwo.render(g);
            }
            for(HUDAmmoObject hao:cannonBalls){
                hao.render(g);
            }
            for(HUDAmmoObject hao:tankCannonBalls){
                hao.render(g);
            }

        }else{
//            g.setColor(Color.black);
//            g.fillRect(x,Game.HEIGHT*Game.SCALE-10,width,10);
        }
    }
    public boolean initHUD=true;
    public void tick(){
        if(isOpened&&!Game.shooting&&currentHud){
                if(initHUD){
                    initHUD=false;
                    setHUDAmmoChords(whichSide);
                }
                hudy-=10;
                if(hudy<=y) hudy=Game.HEIGHT*Game.SCALE-216;
                for(HUDWeaponObject hwo:hudWeaponObjects){
                    hwo.setY(hudy+30);
                }
                for(HUDAmmoObject hao:cannonBalls){
                    hao.setY(hudy+121);
                }
                for(HUDAmmoObject hao:tankCannonBalls){
                    hao.setY(hudy+121);

                }
                if(!choosingAmmo){
                    arrowXY = new Point(x+125,hudy+35);
                }else{
                    arrowXY = new Point(x+125,hudy+142);
                }

                if(weaponChanged){
                    weaponChanged=false;
                    setHUDAmmoChords(whichSide);
                }

        }else if(!isOpened){
            initHUD=true;
            hudy=Game.HEIGHT*Game.SCALE;
            if(isClosing){
                for(HUDWeaponObject hwo:hudWeaponObjects){
                    hwo.setY(hudy);
                }
                for(HUDAmmoObject hao:cannonBalls){
                    hao.setY(hudy);
                }
                for(HUDAmmoObject hao:tankCannonBalls){
                    hao.setY(hudy);

                }
                arrowXY = new Point(x+125,hudy);
                isClosing=false;
            }
        }
    }

    private void setHUDAmmoChords(int side){
        side=whichSide;

        if(side==0){
            weaponType=Handler.playerleft.currentWeaponType;
                if(Handler.playerleft.currentWeaponType==WeaponType.cannon){
                    for(HUDAmmoObject h:cannonBalls){
                        if(h.active) {
                            h.active=false;
                        }
                    }
                }else if(Handler.playerleft.currentWeaponType==WeaponType.tankCannon){
                    for(HUDAmmoObject h:tankCannonBalls){
                        if(h.active){
                            h.active=false;
                        }
                    }
                }
            for(int i=0;i<hudWeaponObjects.size();i++){
                HUDWeaponObject hwo = hudWeaponObjects.get(i);
                if(hwo.isHighlited){
                    if(Handler.playerleft.currentWeaponType==WeaponType.cannon){
                        HUDAmmoObject.weaponType = WeaponType.cannon;
                        for(int j=0;j<cannonBalls.size();j++){
                            HUDAmmoObject hao = cannonBalls.get(j);
                            hao.active=true;
                            hao.setXY(Game.leftHud.getX()+125+121*j+41,Game.leftHud.hudy+122);
                        }
                        for(int j=0;j<tankCannonBalls.size();j++){
                            HUDAmmoObject hao = tankCannonBalls.get(j);
                            hao.active=false;
                            hao.setXY(Game.leftHud.getX()+125+121*j+41,Game.leftHud.hudy+122);
                        }
                    }
                    else if(Handler.playerleft.currentWeaponType==WeaponType.tankCannon) {
                        HUDAmmoObject.weaponType = WeaponType.tankCannon;
                        for(int j=0;j<tankCannonBalls.size();j++){
                            HUDAmmoObject hao = tankCannonBalls.get(j);
                            hao.active=true;
                            hao.setXY(Game.leftHud.getX()+125+121*j+41,Game.leftHud.hudy+122);
                        }
                        for(int j=0;j<cannonBalls.size();j++){
                            HUDAmmoObject hao = cannonBalls.get(j);
                            hao.active=false;
                            hao.setXY(Game.leftHud.getX()+125+121*j+41,Game.leftHud.hudy+122);
                        }

                    }
                }
            }
        }else if(side==1){
            weaponType=Handler.playerleft.currentWeaponType;
            if (Handler.playerright.currentWeaponType == WeaponType.cannon) {
                for (HUDAmmoObject h : cannonBalls) {
                    if (h.active) h.active = false;
                }
            } else if (Handler.playerright.currentWeaponType == WeaponType.tankCannon) {
                for (HUDAmmoObject h : tankCannonBalls) {
                    if (h.active) h.active = false;
                }
            }

            for (int i = 0; i < hudWeaponObjects.size(); i++) {
                HUDWeaponObject hwo = hudWeaponObjects.get(i);
                if (hwo.isHighlited) {
                    if (Handler.playerright.currentWeaponType == WeaponType.cannon) {
                        HUDAmmoObject.weaponType = WeaponType.cannon;
                        for (int j = 0; j < cannonBalls.size(); j++) {
                            HUDAmmoObject hao = cannonBalls.get(j);
                            hao.active = true;
                            hao.setXY(Game.rightHud.getX() + 125 + 121 * j + 41, Game.rightHud.hudy + 122);
                        }
                        for (int j = 0; j < tankCannonBalls.size(); j++) {
                            HUDAmmoObject hao = tankCannonBalls.get(j);
                            hao.active = false;
                            hao.setXY(Game.rightHud.getX() + 125 + 121 * j + 41, Game.rightHud.hudy + 122);
                        }
                    } else if (Handler.playerright.currentWeaponType == WeaponType.tankCannon) {
                        HUDAmmoObject.weaponType = WeaponType.tankCannon;
                        for (int j = 0; j < tankCannonBalls.size(); j++) {
                            HUDAmmoObject hao = tankCannonBalls.get(j);
                            hao.active = true;
                            hao.setXY(Game.rightHud.getX() + 125 + 121 * j + 41, Game.rightHud.hudy + 122);
                        }
                        for (int j = 0; j < cannonBalls.size(); j++) {
                            HUDAmmoObject hao = cannonBalls.get(j);
                            hao.active = false;
                            hao.setXY(Game.rightHud.getX() + 125 + 121 * j + 41, Game.rightHud.hudy + 122);
                        }
                    }
                }
            }
        }
    }
    public void addCannonBall(HUDAmmoObject h){
        cannonBalls.add(h);
    }
    public void addTankCannonBalls(HUDAmmoObject h){
        tankCannonBalls.add(h);
    }

    public void addWeaponHudObject(HUDWeaponObject h){
        hudWeaponObjects.add(h);
    }
    public void removeWeaponHudObject(HUDWeaponObject h){
        hudWeaponObjects.remove(h);
    }

    public boolean isOpened() {
        return isOpened;
    }

    public void setOpened(boolean opened) {
        isOpened = opened;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setClosing(boolean t){
        this.isClosing=t;
    }
    public int nextAmmoX(){
        return x+125+121+41;
    }
    public int nextAmmoY(){
        return y+122;//30+32+60
    }
    public int nextWeaponX(){
        return x+125+121*hudWeaponObjects.size()+25;
    }

//        this.width = 634;//125+25+96+25+96+25+96+25+96+25
//        this.height = 216;//30+32+60+64+30

    public Sprite getAmmoHudSprite() {
        return ammoHudSprite;
    }

    public void setAmmoHudSprite(Sprite ammoHudSprite) {
        this.ammoHudSprite = ammoHudSprite;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }


}
