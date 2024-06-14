import java.awt.Font;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

public class BgObj extends GameObj {
    public BgObj() {
        super();
    }
    
    public BgObj(Image img, int x, int y, double speed) {
        super(img,x,y,speed);
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
