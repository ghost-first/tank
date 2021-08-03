package game;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static game.Constant.*;
//坦克
public abstract class Tank {


    public static final int DIR_UP = 0, DIR_DOWN = 1, DIR_LEFT = 2, DIR_RIGHT = 3; //四个方向
    public static final int ATK_MAX = 200;
    public static final int ATK_MIN = 100;

    private String name;
    private int x, y; //坐标
    public static final int RADIO = 40; //半径
    public static final int DEF_SPEED = 8, DEF_HP = 1000; //默认速度,默认血量
    public static final int STATE_STAND = 0, STATE_MOVE = 1, STATE_DIE = 2; //坦克状态

    private int hp = DEF_HP;
    private int speed = DEF_SPEED;
    private int atk,dir,state = STATE_STAND;
    private Color color;
    private boolean isEnemy = false;
    private BloodBar bar = new BloodBar();
    //TODO 炮弹
    private List<Bullet> bullets = new ArrayList(); //容器
    private List<Explode> explodes = new ArrayList<>();//容器
    public Tank(int x, int y, int dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        initTank();
    }

    public Tank() {
        initTank();
    }

    private void initTank(){
        color = MyUtil.getRandomColor();
        name = MyUtil.getRandomName();
        atk = MyUtil.getRandomNumber(ATK_MIN, ATK_MAX);
    }
    //用于创建敌人坦克

    public boolean isEnemy() {
        return isEnemy;
    }

    public void setEnemy(boolean enemy) {
        isEnemy = enemy;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public static int getDirUp() {
        return DIR_UP;
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

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public List getBullets() {
        return bullets;
    }

    public void setBullets(List bullets) {
        this.bullets = bullets;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void draw(Graphics g){
        logic();
        g.setColor(color); //设置坦克
        drawImgTank(g);
        //绘制坦克的预案
        //g.fillOval(x - RADIO, y - RADIO, RADIO<<1, RADIO<<1); //为什么除以二？

        drawBullets(g);
        drawName(g);
    }


    private void drawName(Graphics g){
        setColor(color);
        g.setFont(Name_font);
        g.drawString(name,x - RADIO/2, y - 20);
        bar.draw(g);
    }

    public abstract void drawImgTank(Graphics g);

    //坦克的逻辑处理
    private void logic(){
        switch(state){
            case STATE_STAND:
            break;
            case STATE_MOVE:
                move();
                break;
            case STATE_DIE:
                break;
        }
    }

    //这是坦克移动的功能
    private int oldX, oldY;
    private void  move(){
        oldX = x;
        oldY = y;
        switch (dir){
            case DIR_UP:
                y -= DEF_SPEED;
                if(y < RADIO){
                    y = RADIO;
                }
                break;
            case DIR_DOWN:
                y += DEF_SPEED;
                if(y > FRAME_HEIGHT- RADIO){
                    y = FRAME_HEIGHT - RADIO;
                }
                break;
            case  DIR_LEFT:
                x -= DEF_SPEED;
                if(x < RADIO/2){
                    x = RADIO/2;
                }
                break;
            case DIR_RIGHT:
                x += DEF_SPEED;
                if(x > FRAME_WIDTH - RADIO){
                    x = FRAME_WIDTH - RADIO;
                }
                break;
        }
    }

    //坦克发射炮弹,创建的子弹添加到坦克管理的容器中
    public void fire(){
        int bulletX = x + 20;
        int bulletY = y + 20;
        Bullet bullet = BulletsPool.get();
        bullet.setX(bulletX);
        bullet.setY(bulletY);
        switch(dir){
            case DIR_UP:
                bulletY -= 2*RADIO;
                break;
            case DIR_DOWN:
                bulletY += 2*RADIO;
                break;
            case DIR_LEFT:
                bulletX -= 2*RADIO;
                break;
            case DIR_RIGHT:
                bulletX += 2*RADIO;
                break;
        }
//        设置子弹属性
//        System.out.println("3");

        bullet.setDir(dir);
        bullet.setAtk(atk);
        bullet.setColor(color);
        bullet.setVisible(true);
//        Bullet bullet = new Bullet(bulletX,bulletY,dir,200,color);
        bullets.add(bullet);
    }

    //将坦克当前发射的所有子弹绘制出来
    private void drawBullets(Graphics g){
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            bullet.draw(g);
        }
//遍历所有的子弹，将不可见的子弹移除并还原对象池
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);

            if(!bullet.isVisible()){
                BulletsPool.GiveBack(bullets.remove(i));
                i--;
            }
        }
    }
    //坦克销毁时处理所有的子弹
    public void bulletsReturn(){
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            BulletsPool.GiveBack(bullet);
        }
        bullets.clear();
    }

    //坦克和敌人的子弹碰撞的方法
    public void collideBullet(List<Bullet> bullets){
        //遍历所有的子弹，检测是否碰撞
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            if(MyUtil.isCollide(this.x, y, RADIO, bullet.getX(), bullet.getY())){
                //子弹消失
                bullet.setVisible(false);
                //坦克受到伤害
                hurt(bullet);
                //爆炸效果
                addExplode(x,y);
            }

        }

    }
    private void addExplode(int x,int y){
        //爆炸效果
        Explode explode = ExplodesPool.get();
        explode.setX(x + RADIO/2);
        explode.setY(y + RADIO/2);
        explode.setVisible(true);
        explode.setIndex(0);
//                explodes.add(new Explode(x + 15,y + 10));
        explodes.add(explode);
    }

    private void hurt(Bullet bullet){
        int atk = bullet.getAtk();
//        System.out.println(atk);
        hp -= atk;
        if(hp <= 0){
            hp = 0;
            die();
        }
    }
//坦克死亡需要处理的内容
    private void die(){
        if(isEnemy){
            GameFrame.killEnemyCount ++;
            EnemyTanksPool.GiveBack(this);
            //判断本关是否结束
            if(GameFrame.isCrossLevel()){

                if (GameFrame.isLastWin()) {
                    GameFrame.setGameState(STATE_WIN);
                } else{
                    //TODO 进入下一关
                    GameFrame.nextLevel();
                }
            }
            //判断游戏是否通关

        }
        else{
            //GameOver
            GameFrame.setGameState(STATE_OVER);
        }
    }

    public boolean isDie(){
        return hp <= 0;
    }
//绘制爆炸
    public void drawExplode(Graphics g){
        for (int i = 0; i < explodes.size(); i++) {
            Explode explode = explodes.get(i);
            explode.drawBoom(g);

        }
        //遍历所有的爆炸，将不可见的爆炸移除并还原对象池
        for (int i = 0; i < explodes.size(); i++) {
            Explode explode = explodes.get(i);
            if(!explode.isVisible()){
                ExplodesPool.GiveBack(explodes.remove(i));
                i--;
            }
        }
    }

    //坦克碰墙后回退
    public void back() {
        x = oldX;
        y = oldY;
    }

    class BloodBar{
        public static final int BAR_LENGTH = 60;
        public static final int BAR_HEIGHT = 5;
        public void draw(Graphics g){
            g.setColor(Color.GRAY); //填充底色
            g.fillRect(x - RADIO/4, y - 5 - BAR_HEIGHT * 2, BAR_LENGTH, BAR_HEIGHT);
            g.setColor(Color.GREEN);
            g.fillRect(x - RADIO/4, y - 5 - BAR_HEIGHT * 2, hp*BAR_LENGTH/DEF_HP, BAR_HEIGHT);

            g.setColor(Color.WHITE);
            g.drawRect(x - RADIO/4, y - 5 - BAR_HEIGHT * 2, BAR_LENGTH, BAR_HEIGHT);
        }
    }
    //坦克的子弹和地图块的碰撞
    public void bulletsCollideMapTiles(List<MapTile> tiles){
        //自己的坦克的子弹和地图块的碰撞
        //使用for-each遍历，在遍历的过程中只能使用迭代器的方式删除元素
        for (int i = 0; i < tiles.size(); i++) {
            MapTile tile = tiles.get(i);
            if (tile.isCollideBullets(bullets)){
                addExplode(tile.getX(),tile.getY());//爆炸效果
                if(tile.getType() == MapTile.TYPE_HARD) continue;  //钢铁墙被击中
                tile.setVisible(false);

                //归还对象池
                MapTilePool.GiveBack(tile);
            }
        }
    }

    //判断坦克与墙的碰撞
    public boolean isCollideTile(List<MapTile> tiles){
        for (int i = 0; i < tiles.size(); i++) {
            MapTile mapTile = tiles.get(i);
            if(mapTile.isVisible() && mapTile.getType() != MapTile.TYPE_COVER) {
                int tileX = mapTile.getX() + MapTile.width / 4;
                int tileY = mapTile.getY() + MapTile.width / 4;
                if (Math.abs(tileX - x) < MapTile.width && Math.abs(tileY - y) < MapTile.width) return true;
            }
        }

        return false;
    }

}
