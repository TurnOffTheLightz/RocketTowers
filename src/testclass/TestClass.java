package testclass;

import classes.Game;
import graphics.Sprite;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.EventQueue;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.nio.Buffer;
/*from  w  w w .  j a  v a2 s .  c  o m*/
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TestClass extends Canvas{
    BufferedImage rotated1;
    BufferedImage rotated2;
    BufferedImage rotated3;

    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    GraphicsDevice gd = ge.getDefaultScreenDevice();
    GraphicsConfiguration gc = gd.getDefaultConfiguration();

    public Sprite sprite;
    public BufferedImage original;
    public TestClass() {

        try {
            original = ImageIO.read(getClass().getResource("/cannon-1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        rotated1 = rotate(original, Math.toRadians(35), gc);
        rotated2 = rotate(original, Math.toRadians(50), gc);
        rotated3 = rotate(original, Math.toRadians(60) , gc);
        JPanel cp = new JPanel();
        cp.add(new JLabel(new ImageIcon(original)));
        cp.add(new JLabel(new ImageIcon(rotated1)));
        cp.add(new JLabel(new ImageIcon(rotated2)));
        cp.add(new JLabel(new ImageIcon(rotated3)));

        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setContentPane(cp);
        f.pack();
        f.setVisible(true);
    }
    public static void main(String[] args) throws IOException {
       TestClass t = new TestClass();
    }


    public BufferedImage rotate(BufferedImage image, double angle,
                                GraphicsConfiguration gc) {
        double sin = Math.abs(Math.sin(angle)), cos = Math.abs(Math.cos(angle));
        int w = image.getWidth(), h = image.getHeight();
        int neww = (int) Math.floor(w * cos + h * sin), newh = (int) Math.floor(h
                * cos + w * sin);
        int transparency = image.getColorModel().getTransparency();
        BufferedImage result = gc.createCompatibleImage(neww, newh, transparency);
        Graphics2D g = result.createGraphics();
        g.translate((neww - w) / 2, (newh - h) / 2);
        g.rotate(angle, w / 2, h / 2);
        g.drawRenderedImage(image, null);
        return result;
    }
}

/**
 * Test Shape.contains
 * @author John B. Matthews
 */
class Contains extends JPanel {

    private static final int SIZE = 400;
    private static final Shape outline = makeShape();
    private Point mouse = new Point();

    public static void main(String args[]) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame f = new JFrame();
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.setResizable(false);
                f.add(new Contains(), BorderLayout.CENTER);
                f.pack();
                f.setVisible(true);
            }
        });
    }

    public Contains() {
        this.setPreferredSize(new Dimension(SIZE, SIZE));
        this.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                mouse = e.getPoint();
                e.getComponent().repaint();
            }
        });
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.setBackground(new Color(0xff, 0xff, 0xc0));
        g2D.clearRect(0, 0, SIZE, SIZE);
        g2D.setPaint(Color.BLACK);
        g2D.drawLine(0, SIZE/2, SIZE, SIZE/2);
        g2D.drawLine(SIZE/2, 0, SIZE/2, SIZE);
        g2D.setPaint(Color.BLUE);
        g2D.fill(outline);
        g2D.setPaint(Color.BLACK);
        g2D.drawString(
                "contains(" + (mouse.x) + ", " + (mouse.y) + ") is "
                        + outline.contains(mouse.x, mouse.y), 10, SIZE - 10);
    }

    /** Create a rotated, scaled and translated outline. */
    private static Shape makeShape() {
        AffineTransform at = new AffineTransform();
        at.translate(SIZE/2, SIZE/2);
        at.scale(33, 33);
        at.rotate(Math.PI/4);
        return at.createTransformedShape(initPoly());
    }

    /** Create a U shaped outline. */
    private static Polygon initPoly() {
        Polygon poly = new Polygon();
        poly.addPoint( 1,  0);
        poly.addPoint( 1, -2);
        poly.addPoint( 2, -2);
        poly.addPoint( 2,  1);
        poly.addPoint(-2,  1);
        poly.addPoint(-2, -2);
        poly.addPoint(-1, -2);
        poly.addPoint(-1,  0);
        return poly;
    }
}