import java.awt.*;

//Background object's settings and drawing
class BgObj extends GameObj {

    public BgObj() {
        super();
    }

    public BgObj(Image img, int x, int y, double speed) {
        super(img,x,y,speed);
    }

    //Implement a scrolling effect 
    @Override
    public void paintSelf(Graphics gImage) {
        super.paintSelf(gImage);
        y += speed;

        if (y >= 0) {
            y = -435;
        }
    }
}