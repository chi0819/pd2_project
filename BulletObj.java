import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JFrame;

public class BulletObj extends GameObj {
<<<<<<< HEAD
    public BulletObj(Image img,int x,int y,int width,int height,double speed,Planewar frame) {
        super(img,x,y,width,height,speed,frame);
    }
=======
    public BulletObj(Image img, int x, int y, int width, int height, double speed, Planewar frame) {
        super(img, x, y, width, height, speed, frame);
    }

>>>>>>> PVPmode_0613
    @Override
    public void paintSelf(Graphics gImage) {
        super.paintSelf(gImage);
        y += speed;
<<<<<<< HEAD
        if (y > 600) {
            this.x = -300; //this coordinate can't be the same as "shell" and "enemy" (or it'll collide)
            this.y = 300;
            GameUtil.removeList.add(this);
        }
        if (/*this.frame.bossObj != null &&*/ this.getRec().intersects(this.frame.planeObj.getRec())) {
            Planewar.state = 3;
            SoundUtil.playSound("sounds/plane_explode.wav",false);
        }
    }
    public Rectangle getRec() {
        return new Rectangle(x,y,width,height);
=======
        if (y > Planewar.height) {
            this.x = -300; // this coordinate can't be the same as "shell" and "enemy" (or it'll collide)
            this.y = 300;
            GameUtil.removeList.add(this);
        }
        if (/* this.frame.bossObj != null && */ this.getRec().intersects(this.frame.planeObj.getRec())) {
            Planewar.currentState = Planewar.GameState.GAMEOVER;
            SoundUtil.playSound("sounds/plane_explode.wav", false);
        }
    }

    public Rectangle getRec() {
        return new Rectangle(x, y, width, height);
>>>>>>> PVPmode_0613
    }
}
