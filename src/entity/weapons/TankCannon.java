package entity.weapons;

import classes.Handler;
import states.Id;
import states.WeaponType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class TankCannon extends Weapon {
    private BufferedImage tankCannonImg;
    public TankCannon(double x, double y, int width, int height, Id id, Handler handler, int whichSide) {
        super(x, y, width, height, id, handler, whichSide);
        this.weaponType = WeaponType.tankCannon;
        try {
        if(whichSide==0){
            tankCannonImg = ImageIO.read(getClass().getResource("/r-1-0.png"));
        }else if(whichSide==1){
            tankCannonImg = ImageIO.read(getClass().getResource("/r-1-1.png"));
        }
        } catch (IOException e) {
            System.out.println("tankCannon constructor error ");
        }
    }

    public void render(Graphics g) {
        if(isActive) g.drawImage(tankCannonImg,drawXY.x,drawXY.y,width,height,null);
    }

    public void tick() {

    }
}
