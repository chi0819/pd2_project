import java.awt.*;

//Boss moving type, detects collisions, and draws health bar.
class BossObj extends GameObj {
    int life = 10;

    public BossObj(Image img,int x,int y,int width,int height,double speed,Planewar frame) {
        super(img,x,y,width,height,speed,frame);
    }


    @Override
    public void paintSelf(Graphics gImage) {
        super.paintSelf(gImage);

        //Boss moves left and right
        if (x > 550 || x < -50) {
            speed = -speed;
        }
        x += speed;

        for (ShellObj shellObj : GameUtil.shellObjList) {

            if (this.getRec().intersects(shellObj.getRec())) {
                shellObj.setX(-100);
                shellObj.setY(100);
                GameUtil.removeList.add(shellObj); //The shell intersects boss should moved out of map and added to removeList
                life--; //If your shell intersects boss, boss life -1 
            }

            if (life <= 0) {
                Planewar.currentState = Planewar.GameState.VICTORY;
            }
        }

        //Painting health bar for boss
        gImage.setColor(Color.white);
        gImage.fillRect(20,40,100,10);
        gImage.setColor(Color.red);
        gImage.fillRect(20,40,(life*100)/10,10);
    }

    public Rectangle getRec() {
        return new Rectangle(x,y,width,height);
    }
}