package input;

import classes.Game;
import classes.HUD.HUD;
import classes.HUD.HUDAmmoObject;
import classes.Handler;
import classes.Player;
import entity.bullets.Ammo;
import graphics.Sprite;
import states.AmmoBoostType;
import states.AmmoType;
import states.GameState;
import states.WeaponType;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener{

    private Handler handler;
    private HUD leftHud,rightHud;
    public static boolean SPACE_HELD = false;
    private boolean readyToFire=false;

    public KeyInput(Handler handler,HUD leftHUD,HUD rightHUD){
        this.handler=handler;
        this.leftHud=leftHUD;
        this.rightHud=rightHUD;
    }

    public void keyTyped(KeyEvent e) {
        //not using
    }



    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
            if(key==KeyEvent.VK_I) {
                if (Game.gameState == GameState.leftMoves) {
                    if (!leftHud.isOpened()) {
                        leftHud.setOpened(true);
                    } else if (leftHud.isOpened()) {
                        leftHud.setOpened(false);
                        leftHud.setClosing(true);
                    }
                } else if (Game.gameState == GameState.rightMoves) {
                    if (!rightHud.isOpened()) {
                        rightHud.setOpened(true);
                    } else if (rightHud.isOpened()) {
                        rightHud.setOpened(false);
                        rightHud.setClosing(true);
                    }
                }
            }else if(key == KeyEvent.VK_ENTER){
                if(Game.gameState==GameState.leftMoves){
                    if(leftHud.isOpened()){
                        if(leftHud.choosingAmmo){
                            if(handler.playerleft.currentWeaponType==WeaponType.cannon){
                                for(HUDAmmoObject hao:leftHud.cannonBalls){
                                    if(hao.isHighlited){
                                        AmmoType at = hao.ammoType;
                                        AmmoBoostType abt = hao.ammoBoostType;
                                        handler.playerleft.currentAmmoType=at;
                                        handler.playerleft.ammoTypeChanged=true;
                                        handler.playerleft.currentAmmoBoostType=abt;
                                        leftHud.setOpened(false);
                                        leftHud.setClosing(true);
                                        leftHud.choosingAmmo=false;
                                        for(Ammo a:Handler.playerleft.ammoList){
                                            if(a.ammoType==at){
                                                Handler.playerleft.setCurrentAmmo(a.getAmmunition());
                                            }
                                        }
                                        for(HUDAmmoObject hh:leftHud.cannonBalls) hh.setHighlited(false);
                                        if(handler.playerleft.currentAmmoType==AmmoType.classic){
                                            if(hao.ammoBoostType== AmmoBoostType.classic) leftHud.setAmmoHudSprite(new Sprite(3,2,Game.sheet));
                                            if(hao.ammoBoostType== AmmoBoostType.classicFrozen) leftHud.setAmmoHudSprite(new Sprite(2,3,Game.sheet));
                                            if(hao.ammoBoostType== AmmoBoostType.classicFire) leftHud.setAmmoHudSprite(new Sprite(1,3,Game.sheet));
                                        }
                                    }
                                }
                            }else if(handler.playerleft.currentWeaponType==WeaponType.tankCannon){
                                for(HUDAmmoObject hao:leftHud.tankCannonBalls){
                                    if(hao.isHighlited){
                                        AmmoType at = hao.ammoType;
                                        AmmoBoostType abt = hao.ammoBoostType;
                                        handler.playerleft.currentAmmoType=at;
                                        handler.playerleft.ammoTypeChanged=true;
                                        handler.playerleft.currentAmmoBoostType=abt;
                                        leftHud.setOpened(false);
                                        leftHud.setClosing(true);
                                        leftHud.choosingAmmo=false;
                                        for(Ammo a:Handler.playerleft.ammoList){
                                            if(a.ammoType==at){
                                                Handler.playerleft.setCurrentAmmo(a.getAmmunition());
                                            }
                                        }
                                        for(HUDAmmoObject hh:leftHud.tankCannonBalls) hh.setHighlited(false);
                                        if(handler.playerleft.currentAmmoType==AmmoType.r_1){
                                            leftHud.setAmmoHudSprite(new Sprite(13,1,64,22,Game.sheet));
                                        }
                                    }
                                }
                            }
                        }else{
                            for(int i=0;i<leftHud.hudWeaponObjects.size();i++){
                                if(leftHud.hudWeaponObjects.get(i).isHighlited){
                                    WeaponType wt = leftHud.hudWeaponObjects.get(i).weaponType;
                                    leftHud.choosingAmmo=true;
                                    HUDAmmoObject.weaponType=wt;
                                    handler.playerleft.currentWeaponType = wt;
                                    handler.playerleft.weaponTypeChanged=true;
                                }
                            }
                        }
                    }
                }else if(Game.gameState==GameState.rightMoves){
                    if(rightHud.isOpened()){
                        if(rightHud.choosingAmmo){
                            if(handler.playerright.currentWeaponType==WeaponType.cannon){
                                for(HUDAmmoObject hao:rightHud.cannonBalls){
                                    if(hao.isHighlited){
                                        AmmoType at = hao.ammoType;
                                        AmmoBoostType abt = hao.ammoBoostType;
                                        handler.playerright.currentAmmoType=at;
                                        handler.playerright.currentAmmoBoostType=abt;
                                        handler.playerright.ammoTypeChanged=true;
                                        rightHud.setOpened(false);
                                        leftHud.setClosing(true);
                                        rightHud.choosingAmmo=false;
                                        for(Ammo a:Handler.playerright.ammoList){
                                            if(a.ammoType==at){
                                                Handler.playerright.setCurrentAmmo(a.getAmmunition());
                                            }
                                        }
                                        for(HUDAmmoObject hh:rightHud.cannonBalls) hh.setHighlited(false);
                                        if(handler.playerright.currentAmmoType==AmmoType.classic){
                                            if(hao.ammoBoostType== AmmoBoostType.classic) rightHud.setAmmoHudSprite(new Sprite(3,2,Game.sheet));
                                            if(hao.ammoBoostType== AmmoBoostType.classicFrozen) rightHud.setAmmoHudSprite(new Sprite(2,3,Game.sheet));
                                            if(hao.ammoBoostType== AmmoBoostType.classicFire) rightHud.setAmmoHudSprite(new Sprite(1,3,Game.sheet));
                                        }
                                    }
                                }
                            }else if(handler.playerright.currentWeaponType==WeaponType.tankCannon){
                                for(HUDAmmoObject hao:rightHud.tankCannonBalls){
                                    if(hao.isHighlited){
                                        AmmoType at = hao.ammoType;
                                        AmmoBoostType abt = hao.ammoBoostType;
                                        handler.playerright.currentAmmoType=at;
                                        handler.playerright.ammoTypeChanged=true;
                                        handler.playerright.currentAmmoBoostType=abt;
                                        rightHud.setOpened(false);
                                        rightHud.setClosing(true);
                                        rightHud.choosingAmmo=false;
                                        for(Ammo a:Handler.playerright.ammoList){
                                            if(a.ammoType==at){
                                                Handler.playerright.setCurrentAmmo(a.getAmmunition());
                                            }
                                        }
                                        for(HUDAmmoObject hh:rightHud.tankCannonBalls) hh.setHighlited(false);
                                        if(handler.playerright.currentAmmoType==AmmoType.r_1){
                                            rightHud.setAmmoHudSprite(new Sprite(13,1,64,22,Game.sheet));
                                        }
                                    }
                                }
                            }
                        }else{
                            for(int i=0;i<rightHud.hudWeaponObjects.size();i++){
                                if(rightHud.hudWeaponObjects.get(i).isHighlited){
                                    WeaponType wt = rightHud.hudWeaponObjects.get(i).weaponType;
                                    rightHud.choosingAmmo=true;
                                    HUDAmmoObject.weaponType=wt;
                                    handler.playerright.currentWeaponType = wt;
                                    handler.playerright.weaponTypeChanged=true;
                                }
                            }
                        }
                    }
                }
            }else if(key == KeyEvent.VK_1){ // hudAmmoObjects at first
                if(Game.gameState==GameState.leftMoves){
                        if(leftHud.isOpened()){
                            if(leftHud.choosingAmmo){
                                if(handler.playerleft.currentWeaponType==WeaponType.cannon){
                                    leftHud.cannonBalls.get(0).setHighlited(true);
                                    for(int j=0;j<leftHud.cannonBalls.size();j++){
                                        if(j!=0) if(leftHud.cannonBalls.get(j).isHighlited)leftHud.cannonBalls.get(j).setHighlited(false);
                                    }
                                }else if(handler.playerleft.currentWeaponType==WeaponType.tankCannon){
                                    leftHud.tankCannonBalls.get(0).setHighlited(true);
                                    for(int j=0;j<leftHud.tankCannonBalls.size();j++){
                                        if(j!=0) if(leftHud.tankCannonBalls.get(j).isHighlited)leftHud.tankCannonBalls.get(j).setHighlited(false);
                                    }
                                }
                            }else{
                                leftHud.hudWeaponObjects.get(0).setHighlited(true);
                                leftHud.weaponChanged=true;
                                handler.playerleft.currentWeaponType = leftHud.hudWeaponObjects.get(0).weaponType;
                                for(int j=0;j<leftHud.hudWeaponObjects.size();j++){
                                    if(j!=0) if(leftHud.hudWeaponObjects.get(j).isHighlited)leftHud.hudWeaponObjects.get(j).setHighlited(false);
                                }
                            }
                        }
                }else if(Game.gameState==GameState.rightMoves){
                    if(rightHud.isOpened()){
                        if(rightHud.choosingAmmo){
                            if(handler.playerright.currentWeaponType==WeaponType.cannon){
                                rightHud.cannonBalls.get(0).setHighlited(true);
                                for(int j=0;j<rightHud.cannonBalls.size();j++){
                                    if(j!=0) if(rightHud.cannonBalls.get(j).isHighlited)rightHud.cannonBalls.get(j).setHighlited(false);
                                }
                            }else if(handler.playerright.currentWeaponType==WeaponType.tankCannon){
                                rightHud.tankCannonBalls.get(0).setHighlited(true);
                                for(int j=0;j<rightHud.tankCannonBalls.size();j++){
                                    if(j!=0) if(rightHud.tankCannonBalls.get(j).isHighlited)rightHud.tankCannonBalls.get(j).setHighlited(false);
                                }
                            }
                        }else{
                            rightHud.hudWeaponObjects.get(0).setHighlited(true);
                            rightHud.weaponChanged=true;
                            handler.playerright.currentWeaponType = rightHud.hudWeaponObjects.get(0).weaponType;
                            for(int j=0;j<rightHud.hudWeaponObjects.size();j++){
                                if(j!=0) if(rightHud.hudWeaponObjects.get(j).isHighlited)rightHud.hudWeaponObjects.get(j).setHighlited(false);
                            }
                        }
                    }
                }
            }else if(key == KeyEvent.VK_2){
                if(Game.gameState==GameState.leftMoves){
                    if(leftHud.isOpened()){
                        if(leftHud.choosingAmmo){
                            if(handler.playerleft.currentWeaponType==WeaponType.cannon){
                                leftHud.cannonBalls.get(1).setHighlited(true);
                                for(int j=0;j<leftHud.cannonBalls.size();j++){
                                    if(j!=1) if(leftHud.cannonBalls.get(j).isHighlited)leftHud.cannonBalls.get(j).setHighlited(false);
                                }
                            }else if(handler.playerleft.currentWeaponType==WeaponType.tankCannon){
                                leftHud.tankCannonBalls.get(1).setHighlited(true);
                                for(int j=0;j<leftHud.tankCannonBalls.size();j++){
                                    if(j!=1) if(leftHud.tankCannonBalls.get(j).isHighlited)leftHud.tankCannonBalls.get(j).setHighlited(false);
                                }
                            }
                        }else{
                            leftHud.hudWeaponObjects.get(1).setHighlited(true);
                            leftHud.weaponChanged=true;
                            handler.playerleft.currentWeaponType = leftHud.hudWeaponObjects.get(1).weaponType;
                            for(int j=0;j<leftHud.hudWeaponObjects.size();j++){
                                if(j!=1) if(leftHud.hudWeaponObjects.get(j).isHighlited)leftHud.hudWeaponObjects.get(j).setHighlited(false);
                            }
                        }
                    }
                }else if(Game.gameState==GameState.rightMoves){
                    if(rightHud.isOpened()){
                        if(rightHud.choosingAmmo){
                            if(handler.playerright.currentWeaponType==WeaponType.cannon){
                                rightHud.cannonBalls.get(1).setHighlited(true);
                            for(int j=0;j<rightHud.cannonBalls.size();j++){
                                if(j!=1) if(rightHud.cannonBalls.get(j).isHighlited)rightHud.cannonBalls.get(j).setHighlited(false);
                            }
                        }else if(handler.playerright.currentWeaponType==WeaponType.tankCannon){
                                rightHud.tankCannonBalls.get(1).setHighlited(true);
                            for(int j=0;j<rightHud.tankCannonBalls.size();j++){
                                if(j!=1) if(rightHud.tankCannonBalls.get(j).isHighlited)rightHud.tankCannonBalls.get(j).setHighlited(false);
                            }
                        }
                        }else{
                            rightHud.hudWeaponObjects.get(1).setHighlited(true);
                            rightHud.weaponChanged=true;
                            handler.playerright.currentWeaponType = rightHud.hudWeaponObjects.get(1).weaponType;
                            for(int j=0;j<rightHud.hudWeaponObjects.size();j++){
                                if(j!=1) if(rightHud.hudWeaponObjects.get(j).isHighlited)rightHud.hudWeaponObjects.get(j).setHighlited(false);
                            }
                        }
                    }
                }
            }else if(key == KeyEvent.VK_3){
                if(Game.gameState==GameState.leftMoves){
                    if(leftHud.isOpened()){
                        if(leftHud.choosingAmmo){
                            if(handler.playerleft.currentWeaponType==WeaponType.cannon){
                                leftHud.cannonBalls.get(2).setHighlited(true);
                                for(int j=0;j<leftHud.cannonBalls.size();j++){
                                    if(j!=2) if(leftHud.cannonBalls.get(j).isHighlited)leftHud.cannonBalls.get(j).setHighlited(false);
                                }
                            }else if(handler.playerleft.currentWeaponType==WeaponType.tankCannon){
                                leftHud.tankCannonBalls.get(2).setHighlited(true);
                                for(int j=0;j<leftHud.tankCannonBalls.size();j++){
                                    if(j!=2) if(leftHud.tankCannonBalls.get(j).isHighlited)leftHud.tankCannonBalls.get(j).setHighlited(false);
                                }
                            }
                        }else{
                            leftHud.hudWeaponObjects.get(2).setHighlited(true);
                            leftHud.weaponChanged=true;
                            handler.playerleft.currentWeaponType = leftHud.hudWeaponObjects.get(2).weaponType;
                            for(int j=0;j<leftHud.hudWeaponObjects.size();j++){
                                if(j!=2) if(leftHud.hudWeaponObjects.get(j).isHighlited)leftHud.hudWeaponObjects.get(j).setHighlited(false);
                            }
                        }
                    }
                }else if(Game.gameState==GameState.rightMoves) {
                    if (rightHud.isOpened()) {
                        if (rightHud.choosingAmmo) {
                                if (handler.playerright.currentWeaponType == WeaponType.cannon) {
                                    rightHud.cannonBalls.get(2).setHighlited(true);
                                    for (int j = 0; j < rightHud.cannonBalls.size(); j++) {
                                        if (j != 2) if (rightHud.cannonBalls.get(j).isHighlited)
                                            rightHud.cannonBalls.get(j).setHighlited(false);
                                    }
                                } else if (handler.playerright.currentWeaponType == WeaponType.tankCannon) {
                                    rightHud.tankCannonBalls.get(2).setHighlited(true);
                                    for (int j = 0; j < rightHud.tankCannonBalls.size(); j++) {
                                        if (j != 2) if (rightHud.tankCannonBalls.get(j).isHighlited)
                                            rightHud.tankCannonBalls.get(j).setHighlited(false);
                                    }
                                } else {
                                rightHud.hudWeaponObjects.get(2).setHighlited(true);
                                rightHud.weaponChanged = true;
                                for (int j = 0; j < rightHud.hudWeaponObjects.size(); j++) {
                                    if (j != 2) if (rightHud.hudWeaponObjects.get(j).isHighlited)
                                        rightHud.hudWeaponObjects.get(j).setHighlited(false);
                                }
                            }
                        }
                    }
                }
            }else if(key == KeyEvent.VK_4){
                if(Game.gameState==GameState.leftMoves){
                    if(leftHud.isOpened()){
                        if(leftHud.choosingAmmo){
                            if(handler.playerleft.currentWeaponType==WeaponType.cannon){
                                leftHud.cannonBalls.get(3).setHighlited(true);
                                for(int j=0;j<leftHud.cannonBalls.size();j++){
                                    if(j!=3) if(leftHud.cannonBalls.get(j).isHighlited)leftHud.cannonBalls.get(j).setHighlited(false);
                                }
                            }else if(handler.playerleft.currentWeaponType==WeaponType.tankCannon){
                                leftHud.tankCannonBalls.get(3).setHighlited(true);
                                for(int j=0;j<leftHud.tankCannonBalls.size();j++){
                                    if(j!=3) if(leftHud.tankCannonBalls.get(j).isHighlited)leftHud.tankCannonBalls.get(j).setHighlited(false);
                                }
                            }
                        }else{
                            leftHud.hudWeaponObjects.get(3).setHighlited(true);
                            leftHud.weaponChanged=true;
                            for(int j=0;j<leftHud.hudWeaponObjects.size();j++){
                                if(j!=3) if(leftHud.hudWeaponObjects.get(j).isHighlited)leftHud.hudWeaponObjects.get(j).setHighlited(false);
                            }
                        }
                    }
                }else if(Game.gameState==GameState.rightMoves){
                    if(rightHud.isOpened()){
                        if(rightHud.choosingAmmo){
                            if(handler.playerright.currentWeaponType==WeaponType.cannon){
                                rightHud.cannonBalls.get(3).setHighlited(true);
                                for(int j=0;j<rightHud.cannonBalls.size();j++){
                                    if(j!=3) if(rightHud.cannonBalls.get(j).isHighlited)rightHud.cannonBalls.get(j).setHighlited(false);
                                }
                            }else if(handler.playerright.currentWeaponType==WeaponType.tankCannon){
                                rightHud.tankCannonBalls.get(3).setHighlited(true);
                                for(int j=0;j<rightHud.tankCannonBalls.size();j++){
                                    if(j!=3) if(rightHud.tankCannonBalls.get(j).isHighlited)rightHud.tankCannonBalls.get(j).setHighlited(false);
                                }
                            }
                        }else{
                            rightHud.hudWeaponObjects.get(3).setHighlited(true);
                            rightHud.weaponChanged=true;
                            for(int j=0;j<rightHud.hudWeaponObjects.size();j++){
                                if(j!=3) if(rightHud.hudWeaponObjects.get(j).isHighlited)rightHud.hudWeaponObjects.get(j).setHighlited(false);
                            }
                        }
                    }
                }
            }else if(key==KeyEvent.VK_SPACE){
                if(!Game.shooting){
                    if(Handler.playerleft.currentPlayer){
                        if(!leftHud.isOpened()){
                            SPACE_HELD = true;
                            readyToFire=true;
                        }
                    }if(Handler.playerright.currentPlayer){
                        if(!rightHud.isOpened()){
                            SPACE_HELD = true;
                            readyToFire=true;
                        }
                    }
                }

            }
    }


    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if(readyToFire)
        if(key == KeyEvent.VK_SPACE&&!Player.ovalDead&&!Player.ovalDeadSwapping){
            SPACE_HELD = false;
            readyToFire=false;
            if(!Game.shooting){
                Game.shooting = true;
                if(handler.playerleft.currentPlayer){
                    for(int i=0;i<handler.playerleft.ammoList.size();i++){
                        Ammo ammo = handler.playerleft.ammoList.get(i);
                        if(ammo.isActive()){
                            ammo.decreaseAmmunition();
                            handler.playerleft.setCurrentAmmo(ammo.getAmmunition());
                        }
                    }
                }else if(handler.playerright.currentPlayer){
                    for(int i=0;i<handler.playerright.ammoList.size();i++){
                        Ammo ammo = handler.playerright.ammoList.get(i);
                        if(ammo.isActive()){
                            ammo.decreaseAmmunition();
                            handler.playerright.setCurrentAmmo(ammo.getAmmunition());
                        }
                    }
                }
            }
        }else if(Player.ovalDead){
            SPACE_HELD=false;
            readyToFire=false;
            Player.ovalDead=false;
        }

    }
}
