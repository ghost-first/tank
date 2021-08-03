package game;

import java.awt.*;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class GameMap {
    public static final int MAP_X = Tank.RADIO * 3;
    public static final int MAP_Y = Tank.RADIO * 3 + 20;
    public static final int MAP_WIDTH = Constant.FRAME_WIDTH - Tank.RADIO*6;
    public static final int MAP_HEIGHT = Constant.FRAME_HEIGHT - Tank.RADIO*8;

    //地图元素块的容器
    private List<MapTile> tiles = new ArrayList<>();
    private Home home;
    public GameMap() {}

    //level 表示第几关
    public void initMap(int level){
        tiles.clear(); //清空
//        随机生成地图块
//        final int COUNT = 20;
//        for (int i = 0; i < COUNT; i++) {
//            MapTile tile = MapTilePool.get();
//            int x,y;
//            do {
//                x = MyUtil.getRandomNumber(MAP_X, MAP_X + MAP_WIDTH - MapTile.width);
//                y = MyUtil.getRandomNumber(MAP_Y, MAP_Y + MAP_HEIGHT - MapTile.width);
//            }while (isCollide(tiles,x,y));
//            tile.setX(x);
//            tile.setY(y);
//            tiles.add(tile);
//
//        }

//        有规律的生成地图块
//        addRow(MAP_X,MAP_Y,5,0,MapTile.width+10);
//        addRow(MAP_X,MAP_Y + MapTile.width*3,5,1,0);
//        addRow(MAP_X,MAP_Y + MapTile.width*6,5,2,MapTile.width+10);
        //初始化大本营
        try {
            loadLevel(level);
        } catch (Exception e){
            e.printStackTrace();
        }
        home = new Home();
    }

    //加载关卡信息
    private void loadLevel(int level) throws Exception{
        //获得单例模式实例
        LevelInof levelInof = LevelInof.getInstance();

        Properties prop = new Properties();
        prop.load(new FileInputStream("Level/lv_"+level+".txt"));
        //将所有的地图信息都加载进来
        int enemyCount = Integer.parseInt(prop.getProperty("enemyCount"));
        String[] enemyType = prop.getProperty("enemyType").split(",");
        int[] type = new int[enemyType.length];
        for (int i = 0; i < type.length; i++) {
            type[i] = Integer.parseInt(enemyType[i]);
        }
        String methodName = prop.getProperty("method");
        int invokeCount = Integer.parseInt(prop.getProperty("invokeCount"));
        String[] params = new String[invokeCount]; //把实参都读到数组中
        for (int i = 1; i <= invokeCount; i++) {
            params[i-1] = prop.getProperty("param"+i);
        }
        //使用读取到的参数，调用对应方法
        invokeMethod(methodName,params);

        levelInof.setEnemyCount(enemyCount);
        levelInof.setEnemyType(type);
        levelInof.setLevel(level);

        String gameLevel = prop.getProperty("gameLevel");
        levelInof.setGameLevel(Integer.parseInt(gameLevel == null? "0" : gameLevel));
    }

    private void invokeMethod(String name, String[] params) {
        for(String param:params){
            String[] split = param.split(",");
            int[] arr = new int[split.length];
            for (int i = 0; i < split.length; i++) {
                arr[i] = Integer.parseInt(split[i]);
            }
            //块之间的间隔时地图块的倍数
            final int DIS = MapTile.width;
            switch (name){
                case "addRow":
                    addRow(MAP_X + arr[0]*DIS,MAP_Y + arr[1]*DIS,arr[2],arr[3],arr[4]*DIS);
                    break;
                case "addCol":
                    addCol(MAP_X + arr[0]*DIS,MAP_Y + arr[1]*DIS,arr[2],arr[3],arr[4]*DIS);
                    break;
                case "addRect":

                    break;
            }

        }
    }

    //不可被摧毁的墙
    public void drawBack(Graphics g){
        for (MapTile tile : tiles) {
            if(tile.getType() != MapTile.TYPE_COVER) {
                tile.draw(g);
            }
        }
        home.draw(g);
    }

    //不可被摧毁的墙
    public void drawCover(Graphics g){
        for (MapTile tile : tiles) {
            if(tile.getType() == MapTile.TYPE_COVER) {
                tile.draw(g);
            }
        }
    }
    //某一个点是否和tiles集合中所有的块有重要部分，有重叠返回true
//    private boolean isCollide(List<MapTile> tiles, int x, int y){
//        for (MapTile tile : tiles) {
//            int tileX = tile.getX();
//            int tileY = tile.getY();
//            if(Math.abs(tileX-x) < MapTile.width && Math.abs(tileY-y) < MapTile.width) return true;
//        }
//        return false;
//    }

    public List<MapTile> getTiles() {
        return tiles;
    }
    //将不可见的地图块移除
    public void clearDestoryTile(){
        for (int i = 0; i < tiles.size(); i++) {
            MapTile tile = tiles.get(i);
            if(!tile.isVisible()){
                tiles.remove(i);
            }
        }
    }

    //容器中添加一行指定类型的地图块,Dis是0，连续；否则不连续
    //TODO 优化
    public void addRow(int startX,int startY,int count,int type,final int Dis){
        for (int i = 0; i < count; i++) {
            MapTile tile = MapTilePool.get();
            tile.setType(type);
            tile.setX(startX + i * (MapTile.width + Dis));
            tile.setY(startY);
            tiles.add(tile);
        }
    }
    public void addCol(int startX,int startY,int count,int type,final int Dis){
        for (int i = 0; i < count; i++) {
            MapTile tile = MapTilePool.get();
            tile.setType(type);
            tile.setX(startX);
            tile.setY(startY + i * (MapTile.width + Dis));
            tiles.add(tile);
        }
    }

}
