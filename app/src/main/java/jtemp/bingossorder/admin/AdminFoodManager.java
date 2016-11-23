package jtemp.bingossorder.admin;

import org.litepal.crud.DataSupport;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import jtemp.bingossorder.entity.EntityFoodCategory;

/**
 * Created by ZMS on 2016/11/22.
 */

public final class AdminFoodManager {

    public static List<EntityFoodCategory> findAllCategory() {
        List<EntityFoodCategory> list = DataSupport.findAll(EntityFoodCategory.class);
        Collections.sort(list, new Comparator<EntityFoodCategory>() {
            @Override
            public int compare(EntityFoodCategory o1, EntityFoodCategory o2) {
                return o1.getCategoryOrder() - o2.getCategoryOrder();
            }
        });
        return list;
    }

    public static void deleteFoodCategory(int id) {
        DataSupport.deleteAll(EntityFoodCategory.class, "id=?", String.valueOf(id));
    }
}
