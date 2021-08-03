package game;

import java.util.ArrayList;
import java.util.List;

public class MapTilePool{
    public static final int DEFAULT_POOL_SIZE = 50;
    public static final int POOL_MAX_SIZE = 70;

    private static List<MapTile> pool = new ArrayList<>();

    static {
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            pool.add(new MapTile());
        }
    }
    //
    public static MapTile get(){
        MapTile mapTile = null;
        if(pool.size() == 0){
            mapTile = new MapTile() {
            };
        }else{//池塘中还有对象，拿走第一个位置的子弹对象
            mapTile = pool.remove(0);
        }
        //验证
//        System.out.println("pool中剩余：" + pool.size());
        return mapTile;
    }
    //
    public static void GiveBack(MapTile mapTile){
        //子弹的个数已经到达了最大值，那就不再归还
        if(pool.size() == POOL_MAX_SIZE){
            return;
        }
        pool.add(mapTile);
        //验证
        //System.out.println("归还后的对象数：" + pool.size());
    }
}
