package jtemp.bingossorder.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jtemp.bingossorder.utils.JsonImp;

/**
 * Created by hasee on 2016/12/3.
 */

public class Foods extends JsonImp {
    //食物名称
    public String name;
//    英文名
    public String EnglishName;
//    网络图片url
    public String src;
//    本地图片url，目前demo是mipmap下资源。
    public int localSrc;
//    种类
    public String kinds;
//    id
    public String id;
//   每日上架时间
    public ArrayList<String> day_onSale_time;
//   每周上架时间
    public List<String> week_onSale_time;
//    价格
    public float price;
//    特价
    public float specialPrice;
//    是否推荐
    public boolean isRecommend;
//    是否上架
    public boolean isOnSale;
//    规格口味忌口
    public HashMap<String,ArrayList<String>>  requirement;
//    排序
    public int sortNum;
//    是否是套餐
    public boolean isCombo;
//    单点限量
    public int limitInOrder;

}
