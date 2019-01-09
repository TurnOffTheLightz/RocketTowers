package entity.utility;

import classes.Game;
import classes.Handler;
import entity.Entity;
import graphics.Sprite;
import states.Id;

import java.awt.*;

public class Coin extends Entity {


    private Sprite[] sprite;
    private int coinFrame = 0,ticks=0;

    public Coin(int x, int y, int width, int height, Id id, Handler handler) {
        super(x, y, width, height, id, handler);
        sprite = new Sprite[7];
        for(int i=0;i<7;i++){
            sprite[i] = new Sprite(i+1,1, Game.sheet);
        }
    }

    public void render(Graphics g) {
        g.drawImage(sprite[coinFrame].getBufferedImage(),(int)x,(int)y,width,height,null);
    }

    public void tick() {
        ticks++;
        if(ticks%10==0){
            coinFrame++;
            if(coinFrame>=6){
                coinFrame=0;
            }
        }
    }

}
