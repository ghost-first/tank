package game;

import java.util.ArrayList;
import java.util.List;

//子弹对象池类
public class BulletsPool {
    public static final int DEFAULT_POOL_SIZE = 200;
    public static final int POOL_MAX_SIZE = 300;
    //用于保存所有子弹的容器
    private static List<Bullet> pool = new ArrayList<>();
    //在类加载的时候创建100个子弹对象添加到容器中
    static {
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            pool.add(new Bullet());
        }
    }
    //从池塘中获取一个子弹对象
    public static Bullet get(){
        Bullet bullet = null;
        if(pool.size() == 0){
            bullet = new Bullet();
        }else{//池塘中还有对象，拿走第一个位置的子弹对象
            bullet = pool.remove(0);
        }
        //验证
//        System.out.println("pool中剩余：" + pool.size());
    return bullet;

    }
    //子弹被销毁的是时候，归还到池塘中
    public static void GiveBack(Bullet bullet){
        //子弹的个数已经到达了最大值，那就不再归还
        if(pool.size() == POOL_MAX_SIZE){
            return;
        }
        pool.add(bullet);
        //验证
        //System.out.println("归还后的对象数：" + pool.size());
    }
}
