package tile.map_components;

import classes.Game;
import classes.Handler;
import graphics.Sprite;
import states.Id;
import tile.Tile;

import java.awt.*;

public class Ground extends Tile {
    private Sprite groundSprite;
    public Ground(int x, int y, int width, int height, Id id, Handler handler) {
        super(x, y, width, height, id, handler);
        groundSprite = new Sprite(2,2, Game.sheet);
        this.hitable=true;
    }


    public void render(Graphics g) {
        g.drawImage(groundSprite.getBufferedImage(),x,y,width,height,null);
    }


    public void tick() {

    }
}
