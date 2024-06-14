import java.awt.Font;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

public class ShellObj extends GameObj {
    public int playerId;

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
        this.playerId = playerId;
    }

    public void paintSelf(Graphics gImage) {
        super.paintSelf(gImage);
        y -= speed;
        // If shell out of screen, remove it
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
