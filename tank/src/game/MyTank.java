package game;

import java.awt.*;

public class MyTank extends Tank {
    private static Image[] tankImg;
    static{
        tankImg = new Image[4];
        tankImg[0] = MyUtil.createImage("tankC0.png");
        tankImg[1] = MyUtil.createImage("tankC1.png");
        tankImg[2] = MyUtil.createImage("tankC2.png");
        tankImg[3] = MyUtil.createImage("tankC3.png");
    }

    public MyTank(int x, int y, int dir) {
        super(x, y, dir);
    }

    public void drawImgTank(Graphics g){
        g.drawImage(tankImg[getDir()], getX(), getY(), RADIO,RADIO,null);
    }

}
