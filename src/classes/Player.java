package classes;

import classes.HUD.HUDAmmoObject;
import entity.Entity;
import entity.bullets.Ammo;
import entity.bullets.CannonBall;
import entity.bullets.R_1;
import entity.bullets.Rocket;
import entity.weapons.TankCannon;
import entity.weapons.Weapon;
import graphics.Sprite;
import input.KeyInput;
import input.MouseInput;
import states.*;
import tile.Tile;
import tile.tower.tower_components.TowerPiece;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class Player{

    public int whichSide;
    public boolean ammoTypeChanged=false;
    public static boolean weaponTypeChanged=false;
    public boolean currentPlayer=false;
    private boolean swap=false;
    private boolean justOneNewAmmo=true;

    public AmmoType currentAmmoType;
    public AmmoBoostType currentAmmoBoostType;
    public WeaponType currentWeaponType;
    public int currentAmmo;
    public double spaceHeldGravity=0.0;

    private Handler handler;


    public LinkedList<Weapon> weapons = new LinkedList<>();
    public ArrayList<Ammo> ammoList = new ArrayList<>();


    public static Ammo ammoObj=null;
    private Point lastShotPoint=new Point(-11,-11);
    private int swapTime=0;
    public static double angle;
    private double velx,vely;

    private Color color1 = new Color(55,0,55), color2 = new Color(150,0,55);
    private int spaceHeldTicks=0,ovalSize=10,ovalShakeTime=0;
    private Point randShake = new Point(0,0);
    private Random rand;
    private double shootingPower;
    public static boolean ovalDead = false;
    public static boolean ovalDeadSwapping = false;

    private double weaponX0,weaponY0,weaponX1,weaponY1;
    private int aimbotX=-1;
    private double spaceHeldVelX=-1;
    private double spaceHeldVelY=-1;

    public Player(int whichSide) {
        this.whichSide = whichSide;
        this.handler = Game.handler;
        rand = new Random();
        if(whichSide==0){
            currentPlayer=true;
            for(Weapon ww:handler.weapons) {
                if (ww.whichSide == 0) {
                    addWeaponToPlayer(ww);
                    weaponX0 = ww.x;
                    weaponY0 = ww.y;

                    if (ww.weaponType == WeaponType.cannon) {
                        ww.setActive(true);
                    }
                }
            }
        }else if(whichSide==1){// weapons default x,y ->2,14 |*32 || x,y ->44,14 |*32
            for(Weapon ww:handler.weapons){
                    if(ww.whichSide==1) {
                        weaponX1 = ww.x;
                        weaponY1 = ww.y;
                        addWeaponToPlayer(ww);
                        if (ww.weaponType == WeaponType.cannon) {
                            ww.setActive(true);
                        }
                    }
                }
        }

        Ammo cannon = new CannonBall(26, 26, Id.ammo, handler, whichSide,AmmoBoostType.classic);
        Ammo cannonFire = new CannonBall(26, 26, Id.ammo, handler, whichSide,AmmoBoostType.classicFire);
        Ammo cannonFrozen = new CannonBall(26, 26, Id.ammo, handler, whichSide,AmmoBoostType.classicFrozen);

        Ammo rocket = new Rocket(26, 26, Id.ammo, handler, whichSide,AmmoBoostType.rocket);
        Ammo r1 = new R_1(64, 32, Id.ammo, handler, whichSide,AmmoBoostType.r_1);

        this.ammoList.add(cannon);
        this.ammoList.add(cannonFire);
        this.ammoList.add(cannonFrozen);

        this.ammoList.add(rocket);
        this.ammoList.add(r1);

        currentAmmo = cannon.getAmmunition();
        currentWeaponType = WeaponType.cannon;
        currentAmmoType = AmmoType.classic;
        currentAmmoBoostType = AmmoBoostType.classic;
        ammoList.get(0).setActive(true);
    }

    public void render(Graphics g){
        if(whichSide==0){
            g.setColor(color1);
        }else if(whichSide==1){
            g.setColor(color2);
        }

        if(!ovalDead&&!ovalDeadSwapping){
            g.fillOval(lastShotPoint.x-ovalSize/2+randShake.x,lastShotPoint.y-ovalSize/2+randShake.y,ovalSize,ovalSize);
//            g.setColor(Color.RED);
//            g.fillRect(aimbotX,Game.HEIGHT*Game.SCALE-116,32,5);
        }
    }
    public void tick() {
        if(currentPlayer){
            if(!Game.shooting){
                angle=getAngle();
                int weaponIndex=0;
                for(int i = 0; i< weapons.size(); i++){
                    Weapon ww = weapons.get(i);
                    if(ww.isActive)weaponIndex=i;
                }
                for(int i = 0; i< weapons.size(); i++){
                    weapons.get(i).setActive(false);
                }
                weapons.get(weaponIndex).setActive(true);
                if(MouseInput.mousePressedX==0&&MouseInput.mousePressedY==0){
                    setLastShotPoint(new Point((int)MouseInput.mouseX,(int)MouseInput.mouseY));
                }
            }
            if(KeyInput.SPACE_HELD){

                spaceHeldTicks++;
                if(spaceHeldTicks>=200) spaceHeldTicks=200;
                if(whichSide==0) color1 = new Color(55+spaceHeldTicks,0,55+spaceHeldTicks/2);
                else color2 = new Color(150+spaceHeldTicks/2,55+spaceHeldTicks/2,0);

                ovalSize+=spaceHeldTicks/30;
                if(ovalSize>=69){
                    ovalSize=69;
                    ovalShakeTime++;
                    int dir = rand.nextInt(2);
                    if(ovalShakeTime<30){
                        switch (dir){
                            case 0://horizontal
                                randShake.x = rand.nextInt(4)-2;
                                for(Weapon w:weapons){
                                    if(w.isActive)w.drawXY.x = (int)w.x+rand.nextInt(2)-1;
                                }
                                break;
                            case 1:
                                randShake.y = rand.nextInt(4)-2;
                                for(Weapon w:weapons){
                                    if(w.isActive)w.drawXY.y = (int)w.y+rand.nextInt(2)-1;
                                }
                                break;
                        }
                    }else if(ovalShakeTime>=30&&ovalShakeTime<60){
                        switch (dir){
                            case 0://horizontal
                                randShake.x = rand.nextInt(6)-3;
                                for(Weapon w:weapons){
                                    if(w.isActive)w.drawXY.x = (int)w.x+rand.nextInt(4)-2;
                                }
                                break;
                            case 1:
                                randShake.y = rand.nextInt(6)-3;
                                for(Weapon w:weapons){
                                    if(w.isActive)w.drawXY.y = (int)w.y+rand.nextInt(3)-2;
                                }
                                break;
                        }
                    }else if(ovalShakeTime>=60&&ovalShakeTime<90){
                        switch (dir){
                            case 0://horizontal
                                randShake.x = rand.nextInt(10)-5;
                                for(Weapon w:weapons){
                                    if(w.isActive)w.drawXY.x = (int)w.x+rand.nextInt(5)-3;
                                }
                                break;
                            case 1:
                                randShake.y = rand.nextInt(10)-5;
                                for(Weapon w:weapons){
                                    if(w.isActive)w.drawXY.y = (int)w.y+rand.nextInt(4)-2;
                                }
                                break;
                        }
                    }
                    else if(ovalShakeTime>=90){
                        for(Weapon w:weapons){
                            if(w.isActive){
                                w.drawXY = new Point((int)w.x,(int)w.y);
                            }
                        }
                        ovalDead=true;
                        ovalDeadSwapping=true;
                    }
                }
            }
            if(ovalDeadSwapping){
                swapTime++;
                if(swapTime>60){
                    ovalDeadSwapping=false;
                    swapTime=0;
                    spaceHeldTicks=0;
                    ovalShakeTime=0;
                    ovalSize=10;
                    color1 = new Color(55,0,55);
                    color2 = new Color(150,55,0);
                    if(Game.gameState==GameState.leftMoves){
                        Game.gameState = GameState.rightMoves;
                        handler.playerleft.currentPlayer=false;
                        handler.playerright.currentPlayer=true;
                    }  else if(Game.gameState==GameState.rightMoves){
                        Game.gameState = GameState.leftMoves;
                        handler.playerright.currentPlayer=false;
                        handler.playerleft.currentPlayer=true;
                    }
                    if(Game.leftHud.isOpened()){
                        Game.leftHud.hudy=0;
                        Game.leftHud.setOpened(false);
                        Game.leftHud.choosingAmmo=false;
                        for(HUDAmmoObject h:Game.leftHud.cannonBalls){
                            h.setHighlited(false);
                        }
                        for(HUDAmmoObject h:Game.leftHud.tankCannonBalls){
                            h.setHighlited(false);
                        }
                    }
                    if(Game.rightHud.isOpened()){
                        Game.rightHud.hudy=0;
                        Game.rightHud.setOpened(false);
                        Game.rightHud.choosingAmmo=false;
                        for(HUDAmmoObject h:Game.rightHud.cannonBalls){
                            h.setHighlited(false);
                        }
                        for(HUDAmmoObject h:Game.rightHud.tankCannonBalls){
                            h.setHighlited(false);
                        }
                    }
                }
            }
            if(weaponTypeChanged){
                weaponTypeChanged=false;
                    for(Weapon ww:weapons){
                        if(ww.weaponType==currentWeaponType){
                            ww.setActive(true);
                            if(ww.whichSide==0){
                                ww.setX(weaponX0);
                                ww.setY(weaponY0);
                            }else if(ww.whichSide==1){
                                ww.setX(weaponX1);
                                ww.setY(weaponY1);
                            }
                        }else{
                            ww.setActive(false);
                        }
                    }

            }
            if(ammoTypeChanged){
                ammoTypeChanged=false;
                for(int k=0;k<ammoList.size();k++){
                    Ammo ammo = ammoList.get(k);
                    ammo.setActive(false);
                }
                for(int k=0;k<ammoList.size();k++){
                    Ammo ammo = ammoList.get(k);
                    if(ammo.ammoType==currentAmmoType){
                        if(ammo.ammoBoostType==currentAmmoBoostType){
                            setCurrentAmmo(ammo.getAmmunition());
                            ammo.setActive(true);
                        }
                    }
                }
            }

            if(Game.shooting){
                int xoffset=0,yoffset=0;
                this.shootingPower = getShootingPower(spaceHeldTicks);
                if(whichSide==0){
                    velx=shootingPower*Math.cos(angle);
                    vely=shootingPower*Math.sin(angle);
                }else if(whichSide==1){
                    velx=-shootingPower*Math.cos(angle);
                    vely=-shootingPower*Math.sin(angle);
                }
                for(int i=0;i<weapons.size();i++) {
                    if (justOneNewAmmo) {
                        justOneNewAmmo = false;
                        Weapon weapon = weapons.get(i);
                        if (currentAmmoType == AmmoType.classic) {//flying colored ammo graphics
                            if(currentAmmoBoostType== AmmoBoostType.classic) ammoObj = new CannonBall(weapon.x, weapon.y, 32,32, Id.ammo, weapon.handler, weapon.whichSide,AmmoBoostType.classic);
                            if(currentAmmoBoostType== AmmoBoostType.classicFrozen) ammoObj = new CannonBall(weapon.x, weapon.y, 32,32, Id.ammo, weapon.handler, weapon.whichSide,AmmoBoostType.classicFrozen);
                            if(currentAmmoBoostType== AmmoBoostType.classicFire) ammoObj = new CannonBall(weapon.x, weapon.y, 32,32, Id.ammo, weapon.handler, weapon.whichSide,AmmoBoostType.classicFire);
                        } else if (currentAmmoType == AmmoType.rocket) {
                            ammoObj = new Rocket(weapon.x, weapon.y, 32,32, Id.ammo, weapon.handler, weapon.whichSide,AmmoBoostType.rocket);
                        }else if (currentAmmoType == AmmoType.r_1) {
                            if(whichSide==0){
                                xoffset=32;
                                yoffset=-22;
                            }else if(whichSide==1){
                                xoffset=0;
                                yoffset=-22;
                            }
                            ammoObj = new R_1(weapon.x+xoffset, weapon.y+yoffset, 64,22, Id.ammo, weapon.handler, weapon.whichSide,AmmoBoostType.r_1);
                        }
                        handler.addAmmunition(ammoObj);
                        ammoObj.setActive(true);
                    }
                }
                if(ammoObj!=null&&!swap){
                    ammoObj.setVelx(velx);
                    ammoObj.gravity += Entity.GRAVITY_POWER;
                    ammoObj.setVely(vely+ammoObj.gravity);
                    for (int k=0;k<handler.tile.size();k++) {
                        Tile t = handler.tile.get(k);
                        if (t.hitable) {
                            if (ammoObj.getBounds().intersects(t.getBounds())) {
                                if(t.isTowerPiece){
                                    for(int i=0;i<handler.towerComponents.size();i++){
                                        TowerPiece tp = handler.towerComponents.get(i);

                                        if(whichSide==0&&t.whichSide==1){
                                            if(tp==t){
                                                tp.decreaseLives();
                                            }
                                        }else if(whichSide==1&&t.whichSide==0){
                                            if(tp==t){
                                                tp.decreaseLives();
                                            }
                                        }

                                    }
                                }
                                handler.removeAmmunition(ammoObj);
                                ammoObj.die();
                                swap=true;
                            }
                        }
                    }
                    if(ammoObj.x<0||ammoObj.x>Game.WIDTH*Game.SCALE){
                        handler.removeAmmunition(ammoObj);
                        ammoObj.die();
                        swap=true;
                    }
                }

                if(swap){
                    swapTime++;
                    if(swapTime>10){
                        swap=false;
                        justOneNewAmmo=true;
                        swapTime=0;
                        spaceHeldTicks=0;
                        ovalSize=10;
                        ovalShakeTime=0;
                        color1 = new Color(55,0,55);
                        color2 = new Color(150,55,0);

                        if(Game.gameState==GameState.leftMoves){
                            Game.shooting = false;
                            Game.gameState = GameState.rightMoves;
                            handler.playerleft.currentPlayer=false;
                            handler.playerright.currentPlayer=true;
                            Game.leftHud.currentHud=false;
                            Game.rightHud.currentHud=true;
                        }  else if(Game.gameState==GameState.rightMoves){
                            Game.shooting = false;
                            Game.gameState = GameState.leftMoves;
                            handler.playerright.currentPlayer=false;
                            handler.playerleft.currentPlayer=true;
                            Game.leftHud.currentHud=true;
                            Game.rightHud.currentHud=false;
                        }
                        if(Game.leftHud.isOpened()){
                            Game.leftHud.setClosing(true);
                            Game.leftHud.setOpened(false);
                        }
                        if(Game.rightHud.isOpened()){
                            Game.leftHud.setClosing(true);
                            Game.rightHud.setOpened(false);
                        }
                    }
                }
            }
        }

    }


    public void addWeaponToPlayer(Weapon weapon){
        weapons.add(weapon);
    }
    public void removeWeaponFromPlayer(Weapon weapon){
        weapons.remove(weapon);
    }

    public void calculateAimbotX(){
        double velx = spaceHeldVelX;
        double vely = spaceHeldVelY;
        double wy=0.0,wx=0.0;
        for(Weapon w:weapons){
            if(w.isActive){
                wy = w.y-116;
                wx=w.x;
            }
        }
        double acceleration = Entity.GRAVITY;
        double range = wx+velx/acceleration*(vely+Math.sqrt(Math.pow(vely,2)+2*acceleration*wy));
        aimbotX= (int) range;
    }

    public double getShootingPower(int ticks){
        double sp = ticks/17.0;
        if(sp<5){
            return 7;
        }else{
            return sp;
        }
    }

    public double getAngle(){
        double x1,y1,x2,y2;
        if(MouseInput.mousePressedY!=0&&MouseInput.mousePressedX!=0){
            x1 = MouseInput.mousePressedX;
            y1 = MouseInput.mousePressedY;
        }else{
            x1 = MouseInput.mouseX;
            y1 = MouseInput.mouseY;
        }
        for(Weapon ww: weapons){
            double angle;
            if(whichSide==0){
                x2 = ww.x+32;
                y2 = ww.y+16;
                angle = Math.atan2(x1-x2,y2-y1)-Math.PI/2;

                if(angle<=Math.PI/6&&angle>=-Math.PI/2){
                    //.
                }
                else if(angle>Math.PI/6) angle =  Math.PI/6;
                else if(angle<-Math.PI/2) angle = -Math.PI/2;

                return angle;
            }else if(whichSide==1){
                x2 = ww.x+64;
                y2 = ww.y+16;
                angle = Math.atan2(x1-x2,y2-y1)+Math.PI/2;

                if(angle>=-Math.PI/6&&angle<=Math.PI/2){
                    //.
                }
                else if(angle<-Math.PI/6) angle =      -Math.PI/6;
                else if(angle>Math.PI/2) angle = Math.PI/2;
                return angle;
            }
        }
        return -1.0;
    }

    public void addAmmo(Ammo ammo){
        ammoList.add(ammo);
    }
    public void removeAmmo(Ammo ammo){
        ammoList.remove(ammo);
    }

    public AmmoType getCurrentAmmoType() {
        return currentAmmoType;
    }

    public void setCurrentAmmoType(AmmoType ammoType) {
        this.currentAmmoType = ammoType;
    }

    public int getCurrentAmmo() {
        return currentAmmo;
    }

    public void setCurrentAmmo(int currentAmmo) {
        this.currentAmmo = currentAmmo;
    }
    public Point getLastShotPoint() {
        return lastShotPoint;
    }

    public void setLastShotPoint(Point lastShotPoint) {
        this.lastShotPoint = lastShotPoint;
    }


    public boolean isCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(boolean currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
    public void setCurrentAmmoBoostType(AmmoBoostType abt){
        this.currentAmmoBoostType=abt;
    }
}
