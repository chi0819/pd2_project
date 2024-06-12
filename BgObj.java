import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JFrame;

<<<<<<< HEAD


=======
>>>>>>> PVPmode_0613
public class BgObj extends GameObj {
    public BgObj() {
        super();
    }
<<<<<<< HEAD
    public BgObj(Image img, int x, int y, double speed) {
        super(img,x,y,speed);
=======

    public BgObj(Image img, int x, int y, double speed) {
        super(img, x, y, speed);
>>>>>>> PVPmode_0613
    }

    @Override
    public void paintSelf(Graphics gImage) {
        super.paintSelf(gImage);
        y += speed;
        if (y >= 0) {
            y = -435;
        }
    }
}
