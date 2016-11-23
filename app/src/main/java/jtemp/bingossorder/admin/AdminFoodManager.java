package jtemp.bingossorder.admin;

import org.litepal.crud.DataSupport;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import jtemp.bingossorder.entity.EntityFoodCategory;
import jtemp.bingossorder.entity.EntityFoodSpec;
import jtemp.bingossorder.entity.FoodSpecType;

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

    public static void deleteFoodSpecByName(String specName) {
        DataSupport.deleteAll(EntityFoodSpec.class, "specName=?", specName);
    }

    public static void saveFoodSpec(EntityFoodSpec entityFoodSpec) {
        deleteFoodSpecByName(entityFoodSpec.getSpecName());
        entityFoodSpec.save();
    }

    public static List<EntityFoodSpec> findAllSpec() {
        List<EntityFoodSpec> list = DataSupport.findAll(EntityFoodSpec.class);

        for (Iterator<EntityFoodSpec> it = list.iterator(); it.hasNext(); ) {
            try {
                FoodSpecType.valueOf(it.next().getSpecType());
            } catch (Exception e) {
                it.remove();
            }
        }

        Collections.sort(list, new Comparator<EntityFoodSpec>() {
            @Override
            public int compare(EntityFoodSpec o1, EntityFoodSpec o2) {
                return FoodSpecType.valueOf(o1.getSpecType()).ordinal() - FoodSpecType.valueOf(o2.getSpecType()).ordinal();
            }
        });

        return list;
    }
}
