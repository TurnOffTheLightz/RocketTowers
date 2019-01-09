package tile.tower.tower_components;

import classes.Game;
import classes.Handler;
import graphics.Sprite;
import states.Id;
import tile.Tile;
import tile.tower.Tower;

import java.awt.*;
import java.util.Random;

public class TowerPiece extends Tile {

    public boolean isHit=false;
    public int lives=2;// 4- 100%, 3-75%, 2- 50%, 1- 25%, 0 - 0%

    private Random rand=new Random();

    public TowerPiece(int x, int y, int width, int height, Id id, Handler handler, int whichSide) { // whichside
        super(x, y, width, height, id, handler,whichSide);
        if(id == Id.towerPiece){
            sprite = new Sprite(9,1,width,height,Game.sheet);
        }else if(id == Id.windowedTowerPiece){
            sprite = new Sprite(7,1,width,height,Game.sheet);
        }else if(id == Id.crownedTowerPiece){
            sprite = new Sprite(11,1,width,height,Game.sheet);
        }
        this.hitable=true;
        this.isTowerPiece=true;

    }
    public TowerPiece(int x, int y, int width, int height, Id id, Handler handler) {// non whichside
        super(x, y, width, height, id, handler);
        if(id == Id.towerPiece){
            sprite = new Sprite(9,1,width,height,Game.sheet);
        }else if(id == Id.windowedTowerPiece){
            sprite = new Sprite(7,1,width,height,Game.sheet);
        }else if(id == Id.crownedTowerPiece){
            sprite = new Sprite(11,1,width,height,Game.sheet);
        }
        this.hitable=false;
        this.isTowerPiece=true;
    }

    public void render(Graphics g) {
        g.drawImage(sprite.getBufferedImage(),x,y,width,height,null);
    }

    public void tick() {

    }

    public void decreaseLives(){
        this.lives--;
        if(lives==0){
            die();
        }
    }
    public void die(){
        super.die();
        handler.removeTowerComponent(this);
    }
}
