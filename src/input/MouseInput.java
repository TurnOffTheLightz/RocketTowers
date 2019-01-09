package input;

import classes.Game;
import classes.HUD.HUD;
import states.GameState;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseInput implements MouseMotionListener,MouseListener {
    private HUD leftHud,rightHud;

    public MouseInput(HUD leftHud,HUD rightHud){
        this.leftHud=leftHud;
        this.rightHud=rightHud;
    }
    public static double mousePressedX;
    public static double mousePressedY;
    public static double mouseX;
    public static double mouseY;


    public void mouseClicked(MouseEvent e) {

    }


    public void mousePressed(MouseEvent e) {
        if(!Game.shooting) {
            int x = e.getX();
            int y = e.getY();
            mousePressedX = x;
            mousePressedY = y;
            if (Game.gameState == GameState.leftMoves) {
                for(int i =0;i< Game.handler.weapons.size();i++){

                }

                if (leftHud.isOpened()) {// ammohudobjects at first
                    for (int i = 0; i < leftHud.hudWeaponObjects.size(); i++) {
                        Rectangle rect = leftHud.hudWeaponObjects.get(i).getBounds();
                        if (rect.contains(x, y)) {
                            leftHud.hudWeaponObjects.get(i).setHighlited(true);
                            for (int j = 0; j < leftHud.hudWeaponObjects.size(); j++) {
                                if (j != i) if (leftHud.hudWeaponObjects.get(j).isHighlited)
                                    leftHud.hudWeaponObjects.get(j).setHighlited(false);
                            }
                        }
                    }
                }
                if (!leftHud.isOpened()) Game.handler.playerleft.setLastShotPoint(new Point(x, y));

            } else if (Game.gameState == GameState.rightMoves) {
                if (rightHud.isOpened()) {
                    for (int i = 0; i < rightHud.hudWeaponObjects.size(); i++) {
                        Rectangle rect = rightHud.hudWeaponObjects.get(i).getBounds();
                        if (rect.contains(x, y)) {
                            rightHud.hudWeaponObjects.get(i).setHighlited(true);
                            for (int j = 0; j < rightHud.hudWeaponObjects.size(); j++) {
                                if (j != i) if (rightHud.hudWeaponObjects.get(j).isHighlited)
                                    rightHud.hudWeaponObjects.get(j).setHighlited(false);
                            }
                        }
                    }
                }
                if (!rightHud.isOpened())
                    Game.handler.playerright.setLastShotPoint(new Point(x, y));
            }
        }
    }


    public void mouseDragged(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
        if(!Game.shooting){
            mouseX = e.getX();
            mouseY = e.getY();
        }
    }

    public void mouseReleased(MouseEvent e) {
        //not using
    }


    public void mouseEntered(MouseEvent e) {
        //not using
    }


    public void mouseExited(MouseEvent e) {
        // not using
    }

}
