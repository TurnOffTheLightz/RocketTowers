package tile.tower.tower_components;

import classes.Game;
import classes.Handler;
import graphics.Sprite;
import states.Id;
import tile.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class TowerPiece extends Tile {

    private final int ATOM_SCALE=8;//1,2,4,8,...
    public boolean isHit=false;
    public int lives=1;// 4- 100%, 3-75%, 2- 50%, 1- 25%, 0 - 0%
    public ArrayList<Atom> atomList = new ArrayList<>();

    private Random rand=new Random();

    public TowerPiece(int x, int y, int width, int height, Id id, Handler handler, int whichSide) { // whichside
        super(x, y, width, height, id, handler,whichSide);
        if(id == Id.towerPiece){
            this.sprite = new Sprite(9,1,width,height,Game.sheet);
        }else if(id == Id.windowedTowerPiece){
            this.sprite = new Sprite(7,1,width,height,Game.sheet);
        }else if(id == Id.crownedTowerPiece){
            this.sprite = new Sprite(11,1,width,height,Game.sheet);
        }
        this.hitable=true;
        this.isTowerPiece=true;

        createAtoms();
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
            for(Atom a:atomList){
                a.die();
            }
        }
    }
    public void die(){
        super.die();
        handler.removeTowerComponent(this);
    }
    public void createAtoms(){
        if(width==64&&height==32){
            for(int w=0;w<ATOM_SCALE;w++){
                for(int h=0;h<ATOM_SCALE/2;h++){
                    Atom atom = new Atom(x+w*ATOM_SCALE,y+h*ATOM_SCALE,w*ATOM_SCALE,h*ATOM_SCALE,ATOM_SCALE,ATOM_SCALE,Id.atom,handler,whichSide,sprite);
                    addAtom(atom);
                }
            }
        }
    }
    public void addAtom(Atom a){atomList.add(a);}
    public void removeAtom(Atom a){atomList.remove(a);}
}
