import java.awt.*;

//Your shell moves upwards
class ShellObj extends GameObj{

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

    //Shell moves upwards
    public void paintSelf(Graphics gImage) {
        super.paintSelf(gImage);
        y -= speed;

        //If shell at the top of window, move it out of window and add it to removeList
        if (y < 0) { 
            this.x = -100;
            this.y = 100;
            GameUtil.removeList.add(this);
        }
    }

    public Rectangle getRec() {
        return new Rectangle(x,y,width,height);
    }
}