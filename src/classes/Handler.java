package classes;

import classes.HUD.HUD;
import entity.Entity;
import entity.bullets.Ammo;
import entity.weapons.Cannon;
import entity.weapons.TankCannon;
import entity.weapons.Weapon;
import states.Id;
import tile.Tile;
import tile.map_components.Grass;
import tile.map_components.Ground;
import tile.tower.tower_components.CannonBox;
import tile.tower.tower_components.TowerPiece;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;

public class Handler {

    public LinkedList<Entity> entity = new LinkedList<Entity>();
    public LinkedList<Tile> tile = new LinkedList<Tile>();
    public LinkedList<TowerPiece> towerComponents = new LinkedList<>();
    public LinkedList<HUD> huds = new LinkedList<HUD>();
    public LinkedList<Weapon> weapons = new LinkedList<>();
    public LinkedList<Ammo> ammoList = new LinkedList<>();
    public LinkedList<Player> playerList = new LinkedList<>();

    public static Player playerleft;
    public static Player playerright;


    private BufferedImage map;

    public Handler(){
        try {
            map = ImageIO.read(getClass().getResource("/map.png"));
        } catch (IOException e) {
            System.out.println("Handler constructor adding map error");
        }
    }

    public void render(Graphics g) {
        for (int i = 0; i < ammoList.size(); i++) {
            ammoList.get(i).render(g);
        }
        for (int i = 0; i < entity.size(); i++) {
            entity.get(i).render(g);
        }
        for (int i = 0; i < weapons.size(); i++) {
            weapons.get(i).render(g);
        }

        for (int i = 0; i < tile.size(); i++) {
            tile.get(i).render(g);
        }
        for (int i = 0; i < towerComponents.size(); i++) {
            towerComponents.get(i).render(g);
        }
        for (int i = 0; i < huds.size(); i++) {
            huds.get(i).render(g);
        }
        playerleft.render(g);
        playerright.render(g);
    }
    public void tick(){
        for(int i=0;i<ammoList.size();i++){
            ammoList.get(i).tick();
        }
        for(int i=0;i<entity.size();i++){
            entity.get(i).tick();
        }
        for(int i=0;i<weapons.size();i++){
            weapons.get(i).tick();
        }
        for(int i=0;i<towerComponents.size();i++){
            towerComponents.get(i).tick();
        }
        for (int i = 0; i < huds.size(); i++) {
            huds.get(i).tick();
        }
        for(int i=0;i<tile.size();i++){
            tile.get(i).tick();
        }
        playerleft.tick();
        playerright.tick();

    }
    public void setPlayerList(){
        playerList.add(playerleft);
        playerList.add(playerright);
    }
    public void addEntity(Entity en){
        entity.add(en);
    }
    public void addTile(Tile t){
        tile.add(t);
    }
    public void addTowerComponent(TowerPiece t){
        towerComponents.add(t);
    }
    public void addHud(HUD h){
        huds.add(h);
    }
    public void addAmmunition(Ammo am){
        ammoList.add(am);
    }
    public void addWeapon(Weapon w){
        weapons.add(w);
    }
    public void removeEntity(Entity en){
        entity.remove(en);
    }
    public void removeTile(Tile t){
        tile.remove(t);
    }
    public void removeTowerComponent(TowerPiece t){
        towerComponents.remove(t);
    }
    public void removeHUD(HUD h){
        huds.remove(h);
    }
    public void removeWeapon(Weapon w){
        weapons.remove(w);
    }
    public void removeAmmunition(Ammo am){ammoList.remove(am);}
    public BufferedImage getMap() {
        return map;
    }

    public void createLevel(BufferedImage map){
        int width = map.getWidth();
        int height = map.getHeight();

        for(int y=0;y<height;y++){
            for(int x=0;x<width;x++){
                int pixel = map.getRGB(x,y);
                int red = (pixel>>16)&0xff;
                int green = (pixel>>8)&0xff;
                int blue=  (pixel)&0xff;
                if(red==255&&green==0&&blue==0){
                    Cannon cannon = new Cannon(x*32,y*32,96,32, Id.weapon,this,0);
                    TankCannon tc = new TankCannon(x*32,y*32,96,32,Id.weapon,this,0);
                    addWeapon(cannon);
                    addWeapon(tc);
                    cannon.readyToShoot=true;
                }
                if(red==254&&green==0&&blue==0){
                    Cannon cannon = new Cannon(x*32,y*32,96,32, Id.weapon,this,1);
                    TankCannon tc = new TankCannon(x*32,y*32,96,32,Id.weapon,this,1);
                    addWeapon(cannon);
                    addWeapon(tc);
                }
                if(red==127&&green==127&&blue==127){
                    TowerPiece t;
                    if(x<15) {
                        t = new TowerPiece(x*32,y*32,64,32, Id.towerPiece,this,0);
                    }
                    else{
                        t = new TowerPiece(x*32,y*32,64,32, Id.towerPiece,this,1);
                    }
                    addTowerComponent(t);
                    addTile(t);
                }
                if(red==33&&green==33&&blue==33){
                    TowerPiece t;
                    if(x<15) {
                        t = new TowerPiece(x*32,y*32,64,32, Id.windowedTowerPiece,this,0);
                    }
                    else{
                        t = new TowerPiece(x*32,y*32,64,32, Id.windowedTowerPiece,this,1);
                    }
                    addTowerComponent(t);
                    addTile(t);
                }
                if(red==86&&green==86&&blue==86){
                    TowerPiece t;
                    if(x<15) {
                        t = new TowerPiece(x*32,y*32,64,32, Id.crownedTowerPiece,this,0);
                    }
                    else{
                        t = new TowerPiece(x*32,y*32,64,32, Id.crownedTowerPiece,this,1);
                    }
                    addTowerComponent(t);
                    addTile(t);
                }
                if(red==0&&green==255&&blue==0){
                    addTile(new Grass(x*32,y*32,32,32,Id.grass,this));
                }
                if(red==50&&green==50&&blue==0){
                    addTile(new Ground(x*32,y*32,32,32,Id.ground,this));
                }

            }
        }
        TowerPiece t;
        t = new TowerPiece(32*2,32*17,64,32,Id.towerPiece,this);//tower0: hidden not hitable
        addTowerComponent(t);
        addTile(t);

        t = new TowerPiece(32*2,32*17,64,32,Id.crownedTowerPiece,this,0);//tower0
        addTowerComponent(t);
        addTile(t);

        t = new TowerPiece(32*45,32*17,64,32,Id.towerPiece,this);//tower1: hidden, not hitable
        addTowerComponent(t);
        addTile(t);

        t = new TowerPiece(32*45,32*17,64,32,Id.crownedTowerPiece,this,1);//tower1:
        addTowerComponent(t);
        addTile(t);

        addTile(new CannonBox(2*32,14*32+25,64,24,Id.cannonBox,this));
        addTile(new CannonBox(45*32,14*32+25,64,24,Id.cannonBox,this));
//        addEntity(new Coin(31,30,64,64,Id.coin,this));
//        addEntity(new Coin(1369,30,64,64,Id.coin,this));
    }
    public void clearLevel(){
        entity.clear();
        tile.clear();
    }
}
