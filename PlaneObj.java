import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Rectangle;
<<<<<<< HEAD
import javax.swing.ImageIcon;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JFrame;
=======
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
>>>>>>> PVPmode_0613

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

<<<<<<< HEAD
public class PlaneObj extends GameObj{
=======
public class PlaneObj extends GameObj {
>>>>>>> PVPmode_0613
    @Override
    public Image getImage() {
        return super.getImage();
    }
<<<<<<< HEAD
    public PlaneObj() {
        super();
    }
    public PlaneObj(Image img,int x,int y,int width,int height,double speed,Planewar frame) {
        super(img,x,y,width,height,speed,frame);
        this.frame.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved (MouseEvent e) {
=======

    public PlaneObj() {
        super();
    }

    // Detect player mouse movement to move the player's plane on screen
    public PlaneObj(Image img, int x, int y, int width, int height, double speed, Planewar frame) {
        super(img, x, y, width, height, speed, frame);
        this.frame.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
>>>>>>> PVPmode_0613
                PlaneObj.super.x = e.getX() - 11;
                PlaneObj.super.y = e.getY() - 16;
            }
        });
    }
<<<<<<< HEAD
=======

    // If player's plane crash bossObj -> GameOver
>>>>>>> PVPmode_0613
    @Override
    public void paintSelf(Graphics gImage) {
        super.paintSelf(gImage);
        if (this.frame.bossObj != null && this.getRec().intersects(this.frame.bossObj.getRec())) {
<<<<<<< HEAD
            Planewar.state = 3;
        }
    }
    public Rectangle getRec() {
        return new Rectangle(x,y,width,height);
=======
            Planewar.currentState = Planewar.GameState.GAMEOVER;
        }
    }

    public Rectangle getRec() {
        return new Rectangle(x, y, width, height);
>>>>>>> PVPmode_0613
    }
}
