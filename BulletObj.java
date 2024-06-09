import java.awt.*;

//Bullet moves, checks collision with you. If out of bounds, removes. If hits you, game over
class BulletObj extends GameObj {

    public BulletObj(Image img,int x,int y,int width,int height,double speed,Planewar frame) {
        super(img,x,y,width,height,speed,frame);
    }

    @Override
    public void paintSelf(Graphics gImage) {

        super.paintSelf(gImage);
        y += speed;

        //Boss bullet added to removeList when it's at the bottom of window 
        if (y > 600) {
            this.x = -300; //this coordinate can't be the same as "shell" and "enemy" (or it'll collide)
            this.y = 300;
            GameUtil.removeList.add(this);
        }

        //If bullet collides with you, the game is over and also play gameover music
        if (/*this.frame.bossObj != null &&*/ this.getRec().intersects(this.frame.planeObj.getRec())) {
            Planewar.currentState = Planewar.GameState.GAMEOVER;
            SoundUtil.playSound("sounds\\plane_explode.wav",false);
        }
    }

    public Rectangle getRec() {
        return new Rectangle(x,y,width,height);
    }
}