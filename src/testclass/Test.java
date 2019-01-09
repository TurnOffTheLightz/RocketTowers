package testclass;

import graphics.Sprite;
import javafx.scene.transform.Affine;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import static classes.Game.frames;
import static java.lang.System.arraycopy;

public class Test extends JPanel implements Runnable
{
    private double width,height;
    private BufferedImage img;
    private int[] pixels;
    private int xx=0,yy=0;
    Thread thread;
    Canvas canvas;
    double which=0.0;
    boolean running =false;
    int frames=0;
    int updates = 0;
    private BufferedImage dstImage,srcImage;
    private Image myImg;
    private ImageIcon myIcon;
    private JLabel myLabel;

    public Test() throws IOException {


        thread = new Thread(this,"QualityLoss");
        canvas = new Canvas();

        setPreferredSize(new Dimension(500,500));

        pixels = updatePixels();
        this.width = img.getWidth();
        this.height = img.getHeight();
        this.add(this.canvas);

        myIcon = new ImageIcon(img);
        myLabel  = new JLabel(myIcon);

        this.add(myLabel);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize((int)width+100,(int)height+100);
        frame.setLocationRelativeTo(null);
        frame.add(this);
        frame.pack();
    }

    public void run() {
        requestFocus();
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        double delta = 0.0;
        double ns = 1000000000.0/60.0;
        while(running){
            long now = System.nanoTime();
            delta+=(now-lastTime)/ns;
            lastTime = now;

            while(delta>=1){
                tick();
                updates++;
                delta--;
            }
            render();
            frames++;
            if(System.currentTimeMillis()-timer>1000){
                timer+=1000;
                System.out.println("FPS: "+frames + "\tUPDATES: "+updates);
                frames=0;
                updates=0;
            }
        }
    }

    protected void tick(){

        if(updates%15==0){
            rotate(-10,width,height);
            this.repaint();
        }
    }
    protected void render(){
        BufferStrategy bs = this.canvas.getBufferStrategy();

        if(bs==null){
            this.canvas.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();


        g.dispose();
        bs.show();
        this.paintComponent(g);
    }

    public synchronized void start(){
        if(running) return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }
    public synchronized void stop(){
        if(!running) return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static BufferedImage asBufferedImage(int[] pixels, int width, int height) {
        ColorModel cm = ColorModel.getRGBdefault();
        int[] bandMasks = new int[] { 0x00ff0000, 0x0000ff00, 0x000000ff, 0xff000000 };
        DataBuffer dataBuffer = new DataBufferInt(pixels, pixels.length);
        WritableRaster raster = Raster.createPackedRaster(
                dataBuffer,
                width,
                height,
                width,
                bandMasks,
                null);
        return new BufferedImage(cm, raster, false, null);
    }

    /** Rotates this image clockwise by an angle in degrees about the point (x, y). */
    public void rotate(double theta, double x, double y) {
        srcImage = asBufferedImage(pixels.clone(), (int)width, (int)height);
        dstImage = asBufferedImage(pixels, (int)width, (int)height);
        Arrays.fill(pixels, 0x00000000);

        Graphics2D g = dstImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.setComposite(AlphaComposite.Src);
        g.transform(AffineTransform.getRotateInstance(theta * (Math.PI / 180), x/2, y/2));

//        g.drawImage(srcImage,100,100,100,56,null);
        g.drawImage(srcImage, 0, 0, this);
        g.dispose();

    }
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
    }



    public void paintImage(double theta, double x, double y){

    }
    public void print() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++)
                System.out.print(pixels[(int)width*y + x]);
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) throws IOException {
        Test t = new Test();
        t.start();
//        t.print();
    }
    protected int[] updatePixels(){
        try {
            this.img = ImageIO.read(getClass().getResource("/kiosk.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (img.getType() != BufferedImage.TYPE_INT_ARGB) {
            BufferedImage tmp = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
            tmp.getGraphics().drawImage(img, 0, 0, null);
            img = tmp;
        }
        return ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
    }

}