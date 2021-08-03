package game;

import java.awt.*;

import static game.Tank.*;

//炮弹
public class Bullet {
//    private static Image[] bulletImg;
//    static{
//        bulletImg = new Image[4];
//        bulletImg[0] = Toolkit.getDefaultToolkit().createImage("Bullet_Red.png");
//    }

    public static final int DEFAULT_SPEED = Tank.DEF_SPEED  * 2;
    public static final int RADIO = 4; //炮弹的半径
    private int x,y;
    private int speed = DEFAULT_SPEED, dir, atk;
    private Color color;
    private boolean visible = true;

    public Bullet(int x, int y, int dir, int atk,Color color) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.atk = atk;
        this.color = color;
    }
//给对象池使用的,所有的属性都是默认值
    public Bullet() {
        this.x = 0;
        this.y = 0;
        this.dir = 0;
        this.atk = 0;
    }

    //炮弹的绘制
    public void draw(Graphics g){
//        int endX = x + 20, endY = y + 20; //炮筒中点坐标
        if(!visible)return;;
        logic();
        g.setColor(color);

        g.fillOval(x,y,RADIO<<1, RADIO<<1);
//        g.drawImage(bulletImg[0],x-2*RADIO,y-2*RADIO, 2*RADIO,2*RADIO,null);
    }

    //子弹的逻辑
    private  void logic(){
        move();
    }


    private void move(){
        switch (dir){
            case Tank.DIR_UP:
                y -= speed;
                if(y <= 0)visible = false;
                break;
            case DIR_DOWN:
                y += speed;
                if(y >= Constant.FRAME_HEIGHT)visible = false;
                break;
            case Tank.DIR_LEFT:
                x -= speed;
                if(x <= 0)visible = false;
                break;
            case Tank.DIR_RIGHT:
                x += speed;
                if(x >= Constant.FRAME_WIDTH)visible = false;
                break;
        }
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
