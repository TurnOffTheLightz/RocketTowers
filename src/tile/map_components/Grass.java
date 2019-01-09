package tile.map_components;

import classes.Game;
import classes.Handler;
import graphics.Sprite;
import states.Id;
import tile.Tile;

import java.awt.*;

public class Grass extends Tile {
    private Sprite grassSprite;
    public Grass(int x, int y, int width, int height, Id id, Handler handler) {
        super(x, y, width, height, id, handler);
        grassSprite = new Sprite(1,2, Game.sheet);
        this.hitable=true;
    }

    public void render(Graphics g) {
    g.drawImage(grassSprite.getBufferedImage(),x,y,width,height,null);
    }

    public void tick() {

    }
}
