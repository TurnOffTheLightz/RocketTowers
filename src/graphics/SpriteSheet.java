package graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SpriteSheet {
    private BufferedImage map;
    public SpriteSheet(String path){
        try {
            map = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public BufferedImage getImage(int x, int y){
        return map.getSubimage(x*32-31,y*32-31,31,31);
    }
    public BufferedImage getImage(int x, int y,int width,int height){
        return map.getSubimage(x*32-31,y*32-31,width-1,height-1);
    }
    public BufferedImage getImage(int x, int y,int width,int height,int offsetX,int offsetY){
        return map.getSubimage(x*32-31+offsetX,y*32-31+offsetY,width-1,height-1);
    }
}
