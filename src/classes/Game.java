package classes;

import graphics.SpriteSheet;
import input.KeyInput;
import input.MouseInput;
import states.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import classes.HUD.HUD;
public class Game extends DrawingPanel implements Runnable{
    //static ints
    public static final int  WIDTH = 390;
    public static final int HEIGHT = WIDTH /14*7;
    public static final int SCALE = 4;
    public static final int LEFT_HUD_X = 25;
    public static final int LEFT_HUD_Y = HEIGHT*SCALE-216;
    public static final int RIGHT_HUD_X = WIDTH*SCALE-270-WIDTH;
    public static final int RIGHT_HUD_Y = HEIGHT*SCALE- 216;

    public static GameState gameState = GameState.leftMoves;
    public Canvas canvas;

    private Thread thread;

    public static Handler handler;
    public static HUD leftHud;
    public static HUD rightHud;
    public static boolean shooting=false;


    public static int frames = 0;
    private Dimension dim = new Dimension(WIDTH*SCALE,HEIGHT*SCALE);


    //booleans
    private boolean running = false;
    public static SpriteSheet sheet;

    public JFrame frame;
    public KeyInput keyIn;


    public Game(){

        this.canvas = new Canvas();
        canvas.setPreferredSize(dim);
        canvas.setMaximumSize(dim);
        canvas.setMinimumSize(dim);
        setPreferredSize(dim);
        setMinimumSize(dim);
        setMaximumSize(dim);

        frame = new JFrame("Rocket Towers:)");
        frame.setResizable(false);
        frame.setSize(dim);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setFocusable(true);
//        frame.setLocationRelativeTo(null);
        frame.setLocation(300,50);
        frame.setVisible(true);
        this.add(this.canvas);
        frame.setContentPane(this);
        frame.pack();

    }

    public static void main(String[] args) {
        Game g = new Game();
        g.start();
    }

    public void init(){
        sheet = new SpriteSheet("/sheet.png");
        handler = new Handler();
        handler.createLevel(handler.getMap());
        handler.playerleft= new Player(0);
        handler.playerright=new Player(1);
        handler.setPlayerList();
        handler.addHud(new HUD(LEFT_HUD_X,LEFT_HUD_Y,0));
        handler.addHud(new HUD(RIGHT_HUD_X,RIGHT_HUD_Y,1));
        leftHud = handler.huds.get(0);
        rightHud = handler.huds.get(1);
        leftHud.currentHud=true;
        keyIn = new KeyInput(handler,leftHud,rightHud);
        canvas.addKeyListener(keyIn);
        this.addKeyListener(keyIn);
        MouseInput ml = new MouseInput(leftHud,rightHud);
        canvas.addMouseListener(ml);
        canvas.addMouseMotionListener(ml);
        this.addMouseListener(ml);
        this.addMouseMotionListener(ml);
    }
    public void run() {
        init();
        requestFocus();
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        double delta = 0.0;
        double ns = 1000000000.0/60.0;
        int updates = 0;
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

    public void render(){
        BufferStrategy bs = this.canvas.getBufferStrategy();

        if(bs==null){
            this.canvas.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(new Color(173,216,230));
        g.fillRect(0,0,getWidth(),getHeight());

        handler.render(g);

        g.dispose();
        bs.show();
        this.paintComponent(g);
    }

    public void tick(){
        handler.tick();
        super.tick();
    }

}
