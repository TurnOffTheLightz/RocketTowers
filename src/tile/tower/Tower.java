package tile.tower;

import classes.Game;
import classes.Handler;
import graphics.Sprite;
import graphics.SpriteSheet;
import states.Id;
import tile.Tile;
import tile.tower.tower_components.TowerPiece;

import java.awt.*;
import java.util.LinkedList;

public class Tower extends Tile{

    private LinkedList<TowerPiece> towerPieces;

    public Tower(int x, int y, int width, int height, Id id, Handler handler, int whichSide) {//whichside
        super(x, y, width, height, id, handler,whichSide);
        this.hitable=true;
        towerPieces = handler.towerComponents;
    }

 public Tower(int x, int y, int width, int height, Id id, Handler handler) {// non whichside
        super(x, y, width, height, id, handler);
        this.hitable=true;
        towerPieces = handler.towerComponents;
    }



    public void render(Graphics g) {

    }

    public void tick() {

    }
}
