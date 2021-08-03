package game;
//工具类

import java.awt.*;

public class MyUtil {

    //min最小值包含，max最大值不包含
    public static final int getRandomNumber(int min, int max){
        return (int)(Math.random()*(max - min) + min);
    }

    public static final Color getRandomColor(){
        int red = getRandomNumber(0,256);
        int blue = getRandomNumber(0,256);
        int green = getRandomNumber(0,256);

        return new Color(red, blue, green);
    }
    //用来判断一个点是否在正方形内部
    public static final boolean isCollide(int rectX, int rectY, int radio, int pointX,int pointY){
        int disX = Math.abs(rectX - pointX);
        int disY = Math.abs(rectY - pointY);
        if(disX < radio && disY  < radio) return true;
        return false;
    }
    //加载图片的方法
    public static final Image createImage(String path){
        return Toolkit.getDefaultToolkit().createImage(path);
    }

    private static final String[] NAMES = {
            "徐凤年","李淳罡","姜泥","青鸟","温华","旋涡鸣人","宇智波佐助","宇智波斑","夏目","黑崎一护",
            "陈长生","徐有容","秋山","唐三十六","落落","苏离","张小凡","碧瑶","陆雪琪","叶凡"
            ,"姜太虚","虚空","狠人","无始","紫月","荒","火灵儿","景天","雪见","徐长卿","紫萱",
            "茂茂","路飞","娜美","乌索普","山治","索隆","乔巴","弗兰奇","罗宾","布鲁克","金木研"
            ,"进击的巨人,","涂山红红","涂山雅雅","涂山蓉蓉","东方月初","涂山苏苏","王权富贵",
            "哆啦A梦","野比大雄","桐人","亚丝娜","宫园薰","王也","不摇碧莲","冯宝宝","天明","项羽",
            "猪小贤","猪一菲","猪小布","猪美嘉","关谷","悠悠","诸葛大力","赵海棠"
    };
    private static final String[] MODIFIED = {
            "可爱","傻傻","萌萌","羞羞","笨笨","呆呆","美丽","聪明","伶俐","狡猾","胖乎乎",
            "粉嫩嫩","白胖胖"
    };
    public static final String getRandomName(){
        return MODIFIED[getRandomNumber(0,MODIFIED.length)] + "的"
                + NAMES[getRandomNumber(0,NAMES.length)];
    }
}
