import java.awt.Image;
import java.awt.Graphics;

public class BgObj extends GameObj {
    public BgObj() {
        super();
    }
    
    public BgObj(Image img, int x, int y, double speed) {
        super(img,x,y,speed);
    }
   
    // Make background image like animation
    @Override
    public void paintSelf(Graphics gImage) {
        super.paintSelf(gImage);
        y += speed;
        if (y >= 0) {
            y = -435;
        }
    }
}
