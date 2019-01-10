package entity.weapons;

import classes.Handler;
import classes.Player;
import entity.Entity;
import input.MouseInput;
import states.Id;
import states.WeaponType;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public abstract class Weapon extends Entity {

    public boolean isActive = false;
    public boolean readyToShoot = false;
    public WeaponType weaponType;
    public Point drawXY;

    public Weapon(double x, double y, int width, int height, Id id, Handler handler, int whichSide) {
        super(x, y, width, height, id, handler);
        this.whichSide=whichSide;
        this.drawXY=new Point((int)x,(int)y);
    }

    public  static BufferedImage rotateImage(BufferedImage rotateImage, double angle) {
//        angle %= 360;
//        if (angle < 0) angle += 360.0;

        AffineTransform tx = new AffineTransform();
        tx.rotate(Math.toRadians(angle), rotateImage.getWidth()/2.0,rotateImage.getHeight() / 2.0);

        double ytrans = 0;
        double xtrans = 0;
        if( angle <= 90 ){
            xtrans = tx.transform(new Point2D.Double(0, rotateImage.getHeight()), null).getX();
            ytrans = tx.transform(new Point2D.Double(0.0, 0.0), null).getY();
        }
        else if( angle <= 180 ){
            xtrans = tx.transform(new Point2D.Double(rotateImage.getWidth(), rotateImage.getHeight()), null).getX();
            ytrans = tx.transform(new Point2D.Double(0, rotateImage.getHeight()), null).getY();
        }
        else if( angle <= 270 ){
            xtrans = tx.transform(new Point2D.Double(rotateImage.getWidth(), 0), null).getX();
            ytrans = tx.transform(new Point2D.Double(rotateImage.getWidth(), rotateImage.getHeight()), null).getY();
        }
        else{
            xtrans = tx.transform(new Point2D.Double(0, 0), null).getX();
            ytrans = tx.transform(new Point2D.Double(rotateImage.getWidth(), 0), null).getY();
        }

        AffineTransform translationTransform = new AffineTransform();
        translationTransform.translate(-xtrans, -ytrans);
        tx.preConcatenate(translationTransform);

        return new AffineTransformOp(tx, AffineTransformOp.TYPE_BICUBIC).filter(rotateImage, null);
    }
    public double getAngle(){
        double x1,y1,x2,y2;
            x1 = MouseInput.mouseX;
            y1 = MouseInput.mouseY;

        double angle;
            if(whichSide==0){
                x2 = x+32;
                y2 = y+16;
                angle = Math.atan2(x1-x2,y2-y1)-Math.PI/2;

                if(angle<=Math.PI/6&&angle>=-Math.PI/2){
                    //.
                }
                else if(angle>Math.PI/6) angle =  Math.PI/6;
                else if(angle<-Math.PI/2) angle = -Math.PI/2;

                return angle;
            }else if(whichSide==1){
                x2 = x+64;
                y2 = y+16;
                angle = Math.atan2(x1-x2,y2-y1)+Math.PI/2;

                if(angle>=-Math.PI/6&&angle<=Math.PI/2){
                    //.
                }
                else if(angle<-Math.PI/6) angle =      -Math.PI/6;
                else if(angle>Math.PI/2) angle = Math.PI/2;
                return angle;
            }
        return -1.0;
    }

    public abstract void render(Graphics g);

    public abstract void tick();

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getWhichSide() {
        return whichSide;
    }
}
