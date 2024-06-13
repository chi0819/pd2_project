import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JFrame;

public class BossObj extends GameObj {
    int basiclife = 10;
    int life = 10;
    int gameLevel = 0;

    public BossObj(Image img, int x, int y, int width, int height, double speed, int gameLevel, Planewar frame) {
        super(img, x, y, width, height, speed, frame);
        this.gameLevel = gameLevel;
        this.life = (gameLevel + 1) * 10;
        this.basiclife = (gameLevel + 1) * 10;
    }

    @Override
    public void paintSelf(Graphics gImage) {
        super.paintSelf(gImage);
        if (x > Planewar.width - 100 || x < -50) {
            speed = -speed;
        }
        x += speed;
        for (ShellObj shellObj : GameUtil.shellObjList) {
            if (this.getRec().intersects(shellObj.getRec())) {
                shellObj.setX(-100);
                shellObj.setY(100);
                GameUtil.removeList.add(shellObj);
                life--;
            }
            if (life <= 0) {
                GameUtil.removeList.add(this);
                Planewar.bossAlive = false;
                if (gameLevel == 2)
                    Planewar.currentState = Planewar.GameState.VICTORY;
            }
        }
        gImage.setColor(Color.white);
        gImage.fillRect(20, 40, 100, 10);
        gImage.setColor(Color.red);
        gImage.fillRect(20, 40, (life * (100)) / basiclife, 10);
    }

    public Rectangle getRec() {
        return new Rectangle(x, y, width, height);
    }
}
