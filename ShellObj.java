import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JFrame;

public class ShellObj extends GameObj{
    @Override
    public Image getImage() {
        return super.getImage();
    }
    public ShellObj() {
        super();
    }
    public ShellObj(Image img,int x,int y,int width,int height,double speed,Planewar frame) {
        super(img,x,y,width,height,speed,frame);
    }
    public void paintSelf(Graphics gImage) {
        super.paintSelf(gImage);
        y -= speed;
        if (y < 0) { //out of the frame
            this.x = -100;
            this.y = 100;
            GameUtil.removeList.add(this);
        }
    }
    public Rectangle getRec() {
        return new Rectangle(x,y,width,height);
    }
}
