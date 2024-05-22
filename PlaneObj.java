package com;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PlaneObj extends GameObj{
    @Override
    public Image getImage() {
        return super.getImage();
    }
    public PlaneObj() {
        super();
    }
    public PlaneObj(Image img,int x,int y,int width,int height,double speed,GameWin frame) {
        super(img,x,y,width,height,speed,frame);
        this.frame.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved (MouseEvent e) {
                PlaneObj.super.x = e.getX() - 11;
                PlaneObj.super.y = e.getY() - 16;
            }
        });
    }
    @Override
    public void paintSelf(Graphics gImage) {
        super.paintSelf(gImage);
        if (this.frame.bossObj != null && this.getRec().intersects(this.frame.bossObj.getRec())) {
            GameWin.state = 3;
        }
    }
    public Rectangle getRec() {
        return new Rectangle(x,y,width,height);
    }
}
