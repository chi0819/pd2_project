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

/*
 * Implement all attributes use in Object on screen
 * Includes : BossObj, BulletObj, EnemyObj, PlaneObj
 */
>>>>>>> PVPmode_0613
public class GameObj {
    Image img;
    int x;
    int y;
    int width;
    int height;
    double speed;
    Planewar frame;

    public Image getImage() {
        return img;
    }

    public void setImage(Image img) {
        this.img = img;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public Planewar getFrame() {
        return frame;
    }

    public void setFrame(Planewar frame) {
        this.frame = frame;
    }

    public GameObj() {

    }
<<<<<<< HEAD
=======

>>>>>>> PVPmode_0613
    public GameObj(Image img, int x, int y, double speed) {
        this.img = img;
        this.x = x;
        this.y = y;
        this.speed = speed;
    }
<<<<<<< HEAD
    public GameObj(Image img,int x,int y,int width,int height,double speed,Planewar frame) {
=======

    public GameObj(Image img, int x, int y, int width, int height, double speed, Planewar frame) {
>>>>>>> PVPmode_0613
        this.img = img;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.frame = frame;
    }

<<<<<<< HEAD
    public void paintSelf (Graphics gImage) {
        gImage.drawImage(img, x, y,null);
    } 
    public Rectangle getRec() {
        return new Rectangle(x,y,width,height);
=======
    public void paintSelf(Graphics gImage) {
        gImage.drawImage(img, x, y, null);
    }

    public Rectangle getRec() {
        return new Rectangle(x, y, width, height);
>>>>>>> PVPmode_0613
    }
}
