import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Rectangle;
<<<<<<< HEAD
import javax.swing.ImageIcon;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
=======
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
>>>>>>> PVPmode_0613
import javax.swing.JPanel;
import javax.swing.JFrame;

public class EnemyObj extends GameObj {
    public EnemyObj() {
        super();
    }
<<<<<<< HEAD
    public EnemyObj(Image img,int x,int y,int width,int height,double speed,Planewar frame) {
        super(img,x,y,width,height,speed,frame);
    }
=======

    public EnemyObj(Image img, int x, int y, int width, int height, double speed, Planewar frame) {
        super(img, x, y, width, height, speed, frame);
    }

>>>>>>> PVPmode_0613
    @Override
    public void paintSelf(Graphics gImage) {
        super.paintSelf(gImage);
        y += speed;
<<<<<<< HEAD
        if (this.getRec().intersects(this.frame.planeObj.getRec())) {
            Planewar.state = 3;
            SoundUtil.playSound("sounds/plane_explode.wav", false);
        }
        if (y > 600) {
=======

        // If player's plane crash enemy -> GameOver
        if (this.getRec().intersects(this.frame.planeObj.getRec())) {
            Planewar.currentState = Planewar.GameState.GAMEOVER;
            SoundUtil.playSound("sounds/plane_explode.wav", false);
        }

        // If enemy out of screen, remove the enemy
        if (y > Planewar.height) {
>>>>>>> PVPmode_0613
            this.x = -200;
            this.y = 200;
            GameUtil.removeList.add(this);
        }
<<<<<<< HEAD
=======

        // If player's bullet hit the enemy, remove the enemy, and increase score
>>>>>>> PVPmode_0613
        for (ShellObj shellObj : GameUtil.shellObjList) {
            if (this.getRec().intersects(shellObj.getRec())) {
                shellObj.setX(-100);
                shellObj.setY(100);
                this.x = -200;
                this.y = 200;
                GameUtil.removeList.add(shellObj);
                GameUtil.removeList.add(this);
                Planewar.score++;
                SoundUtil.playSound("sounds/enemy_explode.wav", false);
            }
        }
    }
<<<<<<< HEAD
    public Rectangle getRec() {
        return new Rectangle(x,y,width,height);
=======

    public Rectangle getRec() {
        return new Rectangle(x, y, width, height);
>>>>>>> PVPmode_0613
    }
}
