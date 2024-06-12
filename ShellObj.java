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

public class ShellObj extends GameObj {
    public int playerId; // 發射子彈的玩家ID

    @Override
    public Image getImage() {
        return super.getImage();
    }

    public ShellObj() {
        super();
    }

    public ShellObj(Image img, int x, int y, int width, int height, double speed, Planewar frame) {
        super(img, x, y, width, height, speed, frame);
    }

    public ShellObj(Image img, int x, int y, int width, int height, int speed, Planewar frame, int playerId) {
        super(img, x, y, width, height, speed, frame);
        this.playerId = playerId; // 設置子彈來源玩家ID
    }

    public void paintSelf(Graphics gImage) {
        super.paintSelf(gImage);
        y -= speed;
        if (y < 0 || y > PVPmode.height) { // out of the frame
            this.x = -100;
            this.y = 100;
            GameUtil.removeList.add(this);
        }
    }

    public Rectangle getRec() {
        return new Rectangle(x, y, width, height);
    }
}
