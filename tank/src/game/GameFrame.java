package game;
//游戏的主窗口类  所有的游戏展示内容都要在该类中实现
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static game.Constant.*;

public class GameFrame extends Frame implements Runnable{
    //定义一张和屏幕大小一致的图片
    private BufferedImage bufImg = new BufferedImage(FRAME_WIDTH,FRAME_HEIGHT,BufferedImage.TYPE_3BYTE_BGR);
//    private Image overImg = MyUtil.createImage(); 重点：这样写一开始游戏就会加载，
//    浪费时间和空间，第一次使用的时候再写
    private static Image overImg = null;
    private static Image winImg = null;
    private static Image backImg = null;

    private static int gameState; //游戏状态
    private static int menuPoint; //菜单指向
    private static Tank myTank;
    private static List<Tank> enemies = new ArrayList<>();
    private static GameMap gameMap = new GameMap();;

    private static int bornEnemyTank; //用于记录本关产生的敌人
    public static int killEnemyCount;
    //进行初始化
    public GameFrame(){
        initFrame();
        keyListener();
        new Thread(this).start(); //刷新窗口的线程
    }

    //进入下一关的方法
    public static void nextLevel() {
        newGame(LevelInof.getInstance().getLevel()+1);
    }

    //对游戏进行初始化
    private void iniGame(){
        gameState = STATE_MENU;
    }
    //属性进行初始化
    private void initFrame(){
        setTitle(GAME_TITLE);
        setSize(FRAME_WIDTH,FRAME_HEIGHT);
        setLocation(FRAME_X,FRAME_Y);
        setResizable(false);//设置窗口大小不可改变
        //设置窗口可见
        setVisible(true);
        //repaint();
    }

    @Override
    //FRAME类的方法，该方法负责所有的绘制内容，所有需要在屏幕中显示的内容，
    // 都要在该方法中调用，该方法不能主动调用，必须通过调用repaint（）去回掉该方法
    public void update(Graphics g1) {
        //得到图片的画笔
        Graphics g = bufImg.getGraphics();
        g.setFont(Game_font);
        switch (gameState){
            case STATE_MENU: drawMenu(g); break;
            case STATE_HELP: drawHelp(g); break;
            case STATE_ABOUT: drawAbout(g); break;
            case STATE_RUN: drawRun(g); break;
            case STATE_OVER: drawDefeat(g); break;
            case STATE_WIN: drawWin(g); break;
        }
        //使用系统画笔将图片绘制到frame
        g1.drawImage(bufImg,0,0,null);
    }

    private void drawDefeat(Graphics g) {
        if(overImg == null) {
            overImg = MyUtil.createImage("defeat.png");
        }
        int imgW = overImg.getWidth(null);
        int imgH = overImg.getHeight(null);
        g.drawImage(overImg,FRAME_WIDTH - imgW >> 1,FRAME_HEIGHT - imgH >> 1, null);
        //添加按键提示信息
        g.setColor(Color.WHITE);
        g.drawString(OVER_STR0,150,FRAME_HEIGHT - 150);
        g.drawString(OVER_STR1,FRAME_WIDTH - 310,FRAME_HEIGHT - 150);
    }

    private void drawRun(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0,0,FRAME_WIDTH,FRAME_HEIGHT);
        //绘制地图
        gameMap.drawBack(g);
        drawEnemies(g);
        myTank.draw(g);
        //不可被摧毁的墙的绘制
        gameMap.drawCover(g);
        //子弹和坦克碰撞
        bulletCollideTank();
        drawExplodes(g);
        bulletCollideWall();
        HPRecover();
        drawTip(g);
    }
    private void drawTip(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(Name_font);

        String task = "需击败坦克：" + LevelInof.getInstance().getEnemyCount();
        String kill = "已击败坦克数：" + killEnemyCount;
        g.drawString(task,10,50);
        g.drawString(kill,10,80);
    }
//绘制所有敌人的坦克
    private void drawEnemies(Graphics g){
        for (int i = 0; i < enemies.size(); i++) {
            Tank enemy = enemies.get(i);
            if(enemy.isDie()){
                enemies.remove(i);
                i--;
                continue;
            }
            enemy.draw(g);
        }
    }

    private void drawAbout(Graphics g) {//TODO
        Information info = Information.getInstance();
        if(info.getAbout() == null) {
            info.setAbout(MyUtil.createImage("About.jpg"));
        }
        g.drawImage(info.getAbout(),0,0,null);
    }

    private void drawHelp(Graphics g) {
        Information info = Information.getInstance();
        if(info.getHelp() == null) {
            info.setHelp(MyUtil.createImage("help.jpg"));
        }
        g.drawImage(info.getHelp(),0,0,null);
    }

    private void drawWin(Graphics g){
        //绘制游戏胜利的界面
        if(winImg == null) {
            winImg = MyUtil.createImage("victory.jpg");
        }
        g.drawImage(winImg,0,40, FRAME_WIDTH,FRAME_HEIGHT,null);
        g.setColor(Color.WHITE);
        Font a = new Font("宋体",Font.BOLD,48);
        g.setFont(a);
        g.drawString("游戏通关!", FRAME_WIDTH/2 - 100, 150);
        g.setFont(Game_font);
        g.drawString(OVER_STR0,150,FRAME_HEIGHT - 150);
        g.drawString(OVER_STR1,FRAME_WIDTH - 310,FRAME_HEIGHT - 150);
    }
    //绘制菜单状态下的方法
    private void drawMenu(Graphics g){
        if(backImg == null) {
            backImg = MyUtil.createImage("back.jpg");
        }
        g.drawImage(backImg,0,30,FRAME_WIDTH,FRAME_HEIGHT,null);

        int x = (FRAME_WIDTH - 50)/ 2;
        int y = FRAME_HEIGHT / 3;
        g.setColor(Color.WHITE);
        for (int i = 0; i < MENUS.length; i++) {
            if(i == menuPoint){
                g.setColor(Color.RED);
            }else{
                g.setColor(Color.WHITE);
            }
            g.drawString(MENUS[i],x,y + 60 * i);


        }
    }

    //点×关闭，窗口监听
    private void keyListener(){
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        //添加按键监听事件
        addKeyListener(new KeyAdapter() {
            @Override//按键被按下的时候被回调的方法
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode(); //被按下键的键值
                switch (gameState){
                    case STATE_MENU: keyPressedEventMenu(keyCode); break;
                    case STATE_HELP: keyPressedEventHelp(keyCode); break;
                    case STATE_ABOUT: keyPressedEventAbout(keyCode); break;
                    case STATE_RUN: keyPressedEventRun(keyCode); break;
                    case STATE_OVER: keyPressedEventDefeat(keyCode); break;
                    case STATE_WIN: keyPressedEventWin(keyCode); break;
                }
            }


            @Override//按键松开时毁掉的内容
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode(); //被按下键的键值
                if(gameState == STATE_RUN){
                   keyReleasedEventRun(keyCode);
               }
            }


        });
    }
    //游戏通关的按键处理
    private void keyPressedEventWin(int keyCode) {
        keyPressedEventDefeat(keyCode);
    }
    //按键松开后的处理方法
    private void keyReleasedEventRun(int keyCode) {
        switch (keyCode){
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                myTank.setState(Tank.STATE_STAND);
                break;
            case KeyEvent.VK_ESCAPE:
                setGameState(STATE_MENU);
                break;

        }
    }


    //菜单状态下按键的处理
    private void keyPressedEventMenu(int keyCode) {
        switch (keyCode){
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                menuPoint--;
                if(menuPoint < 0){
                    menuPoint = MENUS.length - 1;
                }
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                menuPoint++;
                if(menuPoint > 3){
                    menuPoint = 0;
                }
                break;
            case KeyEvent.VK_ENTER:
                switch (menuPoint){
                    case 0:
                        newGame(1);
                        break;
                    case 1:
                        setGameState(STATE_HELP);
                        break;
                    case 2:
                        setGameState(STATE_ABOUT);
                        break;
                    case 3:
                        System.exit(0);
                        break;
                }

                break;
        }

    }

    //开始游戏
    private static void newGame(int level) {
        MusicUtil.playStart();
//        resetGame();
        enemies.clear();
        if(gameMap == null) {
            gameMap = new GameMap();
        }
        gameMap.initMap(level);

        bornEnemyTank = 0;
        killEnemyCount = 0;
        gameState = STATE_RUN;
        myTank = new MyTank(FRAME_WIDTH/2,FRAME_HEIGHT - Tank.RADIO*2,Tank.DIR_UP); //创建坦克对象

        //使用一个单独线程，用于生产敌人的坦克
        new Thread(){
            @Override
            public void run() {
                while (true){
                    if(enemies.size() < ENEMY_MAX_COUNT && LevelInof.getInstance().getEnemyCount() > bornEnemyTank) {
                        bornEnemyTank++;
                        Tank enemy = EnemyTank.creatEnemy();
                        enemies.add(enemy);
                    }
                    try {
                        Thread.sleep(ENEMY_BORN_INTERVAL);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //只在游戏run状态才创建敌人
                    if(gameState != STATE_RUN){
                        break;
                    }
                }
            }
        }.start();

    }

    private void keyPressedEventHelp(int keyCode) {
        keyPressedEventDefeat(keyCode);
    }

    private void keyPressedEventAbout(int keyCode) {
        keyPressedEventDefeat(keyCode);
    }

    private void keyPressedEventRun(int keyCode) {
        switch (keyCode){
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                myTank.setDir(Tank.DIR_UP);
                myTank.setState(Tank.STATE_MOVE);
                break;

            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                myTank.setDir(Tank.DIR_DOWN);
                myTank.setState(Tank.STATE_MOVE);
                break;

            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                myTank.setDir(Tank.DIR_LEFT);
                myTank.setState(Tank.STATE_MOVE);
                break;

            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                myTank.setDir(Tank.DIR_RIGHT);
                myTank.setState(Tank.STATE_MOVE);
                break;

            case KeyEvent.VK_J:
                myTank.fire();
                break;

        }

    }

    private void keyPressedEventDefeat(int keyCode) {
        if(keyCode == KeyEvent.VK_ESCAPE){
            System.exit(0);
        }
        else if(keyCode == KeyEvent.VK_ENTER){
            setGameState(STATE_MENU);
            //一些游戏操作需要关闭，一些属性需要重置
            resetGame();
        }
    }

    private void resetGame(){
        killEnemyCount = 0;
        menuPoint = 0;
        //先让自己坦克的子弹还回对象池
        myTank = null;
        //清空敌人
        for (int i = 0; i < enemies.size(); i++) {
            Tank enemy = enemies.get(i);
            enemy.bulletsReturn();
        }
        enemies.clear();
        //清空地图资源
        gameMap = null;

    }

    @Override
    public void run() {
        while(true){
            repaint();
            try {
                Thread.sleep(RRPAINT_Interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    private void bulletCollideTank(){
        for (int i = 0; i < enemies.size(); i++) {
            Tank enemy = enemies.get(i);
            //我的坦克的子弹和所有敌人坦克的碰撞
            enemy.collideBullet(myTank.getBullets());
        }
        //敌人坦克子弹和我的坦克的碰撞
        for (int i = 0; i < enemies.size(); i++) {
            Tank enemy = enemies.get(i);
            myTank.collideBullet(enemy.getBullets());

        }
    }

    private void bulletCollideWall(){
        myTank.bulletsCollideMapTiles(gameMap.getTiles());
        for (int i = 0; i < enemies.size(); i++) {
            Tank enemy = enemies.get(i);
            enemy.bulletsCollideMapTiles(gameMap.getTiles());
        }
        //坦克和地图的碰撞
        if (myTank.isCollideTile(gameMap.getTiles())) {
            myTank.back();
        }
        for (int i = 0; i < enemies.size(); i++) {
            Tank enemy = enemies.get(i);
            if(enemy.isCollideTile(gameMap.getTiles())){
                enemy.back();
            }
        }
        gameMap.clearDestoryTile();
    }
    //所有坦克上的爆炸效果
    private void drawExplodes(Graphics g){
        for (int i = 0; i < enemies.size(); i++) {
            Tank enemy = enemies.get(i);
            enemy.drawExplode(g);
        }
        myTank.drawExplode(g);
    }
    private void HPRecover(){
        int a = Home.HOME_X;
        int b = Home.HOME_X + 3*MapTile.width;
        int c = Home.HOME_Y;
        if(myTank.getX() > a && myTank.getX() < b && myTank.getY() > c && myTank.getHp() <= 1000){
            myTank.setHp(myTank.getHp() + 50);
        }
    }

    public static int getGameState() {
        return gameState;
    }

    public static void setGameState(int gameState) {
        GameFrame.gameState = gameState;
    }

    public static boolean isLastWin(){
        int currLevel = LevelInof.getInstance().getLevel(); //当前关卡
        int levelCount = GameInfo.getLevelCount(); //总关卡
        return currLevel == levelCount;

    }

    //判断是否过关
    public static boolean isCrossLevel(){
        //消灭敌人的数量 和关卡最大敌人数量一致
        System.out.println("killEnemyCount:" + killEnemyCount);
        return killEnemyCount == LevelInof.getInstance().getEnemyCount();
    }
}
