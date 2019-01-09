package tile.tower.tower_components;

import classes.Game;
import classes.Handler;
import graphics.Sprite;
import states.Id;
import tile.Tile;

import java.awt.*;

public class CannonBox extends Tile{
    public CannonBox(int x, int y, int width, int height, Id id, Handler handler) {
        super(x, y, width, height, id, handler);
        this.sprite = new Sprite(10,2, width,height,Game.sheet);
        this.hitable=false;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(sprite.getBufferedImage(),x,y,width,height,null);
    }

    @Override
    public void tick() {

    }
}
