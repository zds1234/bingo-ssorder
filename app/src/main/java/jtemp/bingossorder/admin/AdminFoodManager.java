package jtemp.bingossorder.admin;

import org.litepal.crud.DataSupport;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import jtemp.bingossorder.entity.EntityFood;
import jtemp.bingossorder.entity.EntityFoodCategory;
import jtemp.bingossorder.entity.EntityFoodCategoryRelationMapping;
import jtemp.bingossorder.entity.EntityFoodSpec;
import jtemp.bingossorder.entity.FoodSpecType;

/**
 * Created by ZMS on 2016/11/22.
 */

public final class AdminFoodManager {

    /**
     * 所有菜品类别
     *
     * @return
     */
    public static List<EntityFoodCategory> findAllCategory() {
        List<EntityFoodCategory> list = DataSupport.findAll(EntityFoodCategory.class);
        Collections.sort(list, new Comparator<EntityFoodCategory>() {
            @Override
            public int compare(EntityFoodCategory o1, EntityFoodCategory o2) {
                return o1.getCategoryOrder() - o2.getCategoryOrder();
            }
        });
        for (EntityFoodCategory category : list) {
            if (category.isTaocan()) {
                List<EntityFoodCategoryRelationMapping> mappings = DataSupport.where("categoryId=?", String.valueOf(category.getId()))
                        .find(EntityFoodCategoryRelationMapping.class);
                category.getRelationCategory().clear();
                for (EntityFoodCategoryRelationMapping mapping : mappings) {
                    for (EntityFoodCategory relation : list) {
                        if (mapping.getRelationCategoryId() == relation.getId() && relation != category) {
                            category.addRelationCategory(relation);
                            break;
                        }
                    }
                }
            }
        }
        return list;
    }

    /**
     * 保存菜品类别
     *
     * @param entityFoodCategory
     * @return
     */
    public static boolean saveFoodCategoryIfNotExists(EntityFoodCategory entityFoodCategory) {
        if (DataSupport.isExist(EntityFoodCategory.class, "categoryName=?", entityFoodCategory.getCategoryName())) {
            return false;
        }
        saveFoodCategory(entityFoodCategory);
        return true;
    }

    public static void saveFoodCategory(EntityFoodCategory foodCategory) {
        //保存
        foodCategory.save();
        //删除映射
        DataSupport.deleteAll(EntityFoodCategoryRelationMapping.class, "categoryId=?", String.valueOf(foodCategory.getId()));
        //保存新映射
        if (foodCategory.isTaocan()) {
            for (EntityFoodCategory relation : foodCategory.getRelationCategory().values()) {
                if (relation != null
                        && relation != foodCategory
                        && relation.getCategoryName() != foodCategory.getCategoryName()
                        && relation.getId() != foodCategory.getId()) {
                    EntityFoodCategoryRelationMapping mapping = new EntityFoodCategoryRelationMapping();
                    mapping.setCategoryId(foodCategory.getId());
                    mapping.setRelationCategoryId(relation.getId());
                    mapping.save();
                }
            }
        }
    }

    /**
     * 删除菜品类别
     *
     * @param id
     */
    public static void deleteFoodCategory(int id) {
        DataSupport.deleteAll(EntityFoodCategory.class, "id=?", String.valueOf(id));
    }

    /**
     * 删除菜品规格
     *
     * @param specName
     */
    public static void deleteFoodSpecByName(String specName) {
        DataSupport.deleteAll(EntityFoodSpec.class, "specName=?", specName);
    }

    /**
     * 保存菜品规格
     *
     * @param entityFoodSpec
     */
    public static void saveFoodSpec(EntityFoodSpec entityFoodSpec) {
        deleteFoodSpecByName(entityFoodSpec.getSpecName());
        entityFoodSpec.save();
    }

    /**
     * 所有菜品规格
     *
     * @return
     */
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

    /**
     * 保存食品
     *
     * @param food
     */
    public static void saveFood(EntityFood food) {
    }
}
