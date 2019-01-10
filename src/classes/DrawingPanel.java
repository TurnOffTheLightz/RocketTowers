package classes;

import entity.bullets.Ammo;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.util.LinkedList;

import static classes.Player.ammoObj;

public class DrawingPanel extends JPanel {

    int[]pixels;
    BufferedImage img;
    public DrawingPanel(){
//        pixels = loadImage(img);
    }
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        if(Game.shooting&&ammoObj!=null){
//            ammoObj.rotate(pixels);
        }

    }
    public void tick(){
        if(Game.shooting){
            if(ammoObj!=null){
//                this.add(new JLabel(new ImageIcon(ammoObj.dstImage)));
            }
        }
    }
    public int[]loadImage(BufferedImage img){
        if (img.getType() != BufferedImage.TYPE_INT_ARGB) {
            BufferedImage tmp = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
            tmp.getGraphics().drawImage(img, 0, 0, null);
            img = tmp;
        }
        return ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
    }
    }
