package jtemp.bingossorder.manager;

import android.text.TextUtils;
import android.util.Log;

import com.eunut.util.T;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import jtemp.bingossorder.activity.R;
import jtemp.bingossorder.model.Foods;

/**
 * Created by hasee on 2016/12/3.
 */

public class FoodInfoManager extends Observable {
    private static List<Foods>  foodsList ;

    private static  FoodInfoManager instance ;

    private FoodInfoManager() {
    }

    public static FoodInfoManager getInstance() {
        if(instance==null){
            synchronized(FoodInfoManager.class) {
                instance =new FoodInfoManager();
            }
        }
        return instance;
    }
    //原则上应该不让直接获取本地数据的资源，只能通过相关方法介入。

    // 获取食物列表
    public static List<Foods> getFoodsList() {
        ArrayList<Foods> temp =new ArrayList<>();
        if(foodsList!=null) {
            temp.addAll(foodsList);
        }else{
            temp.addAll(getLocalData());
        }
        return temp;
    }
    //获取本地存储数据
    private static List<Foods> getLocalData() {
       if(1==1){
           foodsList =new ArrayList<>();
           for (int i=0;i<20;i++){
               Foods f=new Foods();
               f.localSrc= R.mipmap.food;
               f.name="洪门大虾";
               foodsList.add(f);
           }
           return foodsList;
       }

        return new ArrayList<Foods>();
    }

    public static List<Foods> getFoodsByKinds(String kinds){
        ArrayList<Foods> temp =new ArrayList<>();
        if(1==1){
//            测试逻辑，正常应该去掉
            Foods f=new Foods();
            f.kinds ="全部";
            temp.add(f);
            for (int i=0;i<10;i++){
                Foods f1=new Foods();
                f1.kinds="小吃"+i;
                temp.add(f1);
            }
        }else{
//            先添加 所有的食物。
            temp.addAll(getFoodsList());
//            入股种类为null，默认获取全部
            if(TextUtils.isEmpty(kinds)){

                return temp;
            }else{
//                根据字段查找。
                try {
                    temp= (ArrayList<Foods>) searchByField(temp,"kinds",kinds);
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {

                }
            }

        }
        return temp;
    }
        /**
     *  @param list  数据源
     * @param field_name 字段名
         * @param  search_by 查找条件
 *
 *  a="a"  */
    public static  List<Foods> searchByField (List<Foods> list, String field_name, String search_by)throws Exception{
        List<Foods> tempList=new ArrayList<>();
        if(TextUtils.isEmpty(search_by)) return tempList;
        tempList.clear();
        for(int i=0;i<list.size();i++){
           Field field= list.get(i).getClass().getDeclaredField(field_name);
            field.setAccessible(true);
            String temp= (String) field.get(list.get(i));
            if(temp!=null){
                Log.i("temp",temp.toString()+temp.getClass());
            }
            if(!TextUtils.isEmpty(temp)&&temp.contains(search_by)){
                tempList.add(list.get(i));

            }
        }
        return tempList;
    }


}
