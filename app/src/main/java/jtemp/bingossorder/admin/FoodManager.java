package jtemp.bingossorder.admin;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import jtemp.bingossorder.code.ErrorCode;
import jtemp.bingossorder.code.FoodSpecType;
import jtemp.bingossorder.entity.FoodCategory;
import jtemp.bingossorder.entity.FoodSpec;

/**
 * Created by ZMS on 2016/11/22.
 * 菜品编辑器
 */
public final class FoodManager {

    //------菜品类别------

    /**
     * 添加菜品类别
     *
     * @param name
     * @param purchaseLimit
     * @param sort
     * @param combo
     * @param relations
     * @return
     */
    public static ErrorCode addFoodCategory(String name,
                                            int purchaseLimit,
                                            int sort,
                                            boolean combo,
                                            FoodCategory... relations) {

        if (DataSupport.isExist(FoodCategory.class, "name=?", name)) {
            return ErrorCode.FOOD_CATEGORY_EXISTS;
        }
        FoodCategory foodCategory = createFoodCategory(name, purchaseLimit, sort, combo, relations);
        foodCategory.save();
        return ErrorCode.SUCCESS;
    }

    private static FoodCategory createFoodCategory(String name, int purchaseLimit, int sort, boolean combo, FoodCategory... relations) {
        FoodCategory category = new FoodCategory();
        category.setName(name);
        category.setPurchaseLimit(purchaseLimit);
        category.setSort(sort);
        category.setCombo(combo);
        if (category.isCombo()) {
            StringBuilder sb = new StringBuilder();
            category.setRelationsCategory(Arrays.asList(relations));
            for (FoodCategory c : relations) {
                sb.append(c.getId()).append(";");
            }
            category.setRelations(sb.toString());
        }
        return category;
    }

    /**
     * 删除菜品类别
     *
     * @param id
     * @return
     */
    public static ErrorCode deleteFoodCategory(long id) {
        DataSupport.delete(FoodCategory.class, id);
        return ErrorCode.SUCCESS;
    }

    /**
     * 更新菜品类别
     *
     * @param id
     * @param name
     * @param purchaseLimit
     * @param sort
     * @param combo
     * @param relations
     * @return
     */
    public static ErrorCode updateFoodCategory(long id,
                                               String name,
                                               int purchaseLimit,
                                               int sort,
                                               boolean combo,
                                               FoodCategory... relations) {
        FoodCategory category = createFoodCategory(name, purchaseLimit, sort, combo, relations);
        category.update(id);
        return ErrorCode.SUCCESS;
    }

    /**
     * 查找所有菜品类别
     *
     * @return
     */
    public static List<FoodCategory> findAllFoodCategory() {
        List<FoodCategory> list = DataSupport.findAll(FoodCategory.class);
        Collections.sort(list, new Comparator<FoodCategory>() {
            @Override
            public int compare(FoodCategory o1, FoodCategory o2) {
                return o1.getSort() - o2.getSort();
            }
        });
        for (FoodCategory category : list) {
            if (category.isCombo()) {
                List<FoodCategory> relationCategory = new ArrayList<>();
                String[] array = category.getRelations().split(";");
                for (String rid : array) {
                    for (FoodCategory fc : list) {
                        if (rid.equals(String.valueOf(fc.getId()))) {
                            relationCategory.add(fc);
                        }
                    }
                }
                category.setRelationsCategory(relationCategory);
            }
        }
        return list;
    }

    //---------菜品规格-----------

    /**
     * 添加菜品规格
     *
     * @param specType
     * @param name
     * @return
     */
    public static ErrorCode addFoodSpec(FoodSpecType specType, String name) {
        deleteFoodSpec(name);
        FoodSpec spec = new FoodSpec();
        spec.setSpecName(name);
        spec.setSpecType(specType.name());
        return ErrorCode.SUCCESS;
    }

    /**
     * 删除菜品规格
     *
     * @param name
     * @return
     */
    public static ErrorCode deleteFoodSpec(String name) {
        DataSupport.deleteAll(FoodSpec.class, "specName=?", name);
        return ErrorCode.SUCCESS;
    }

    /**
     * 获取所有菜品类别
     *
     * @return
     */
    public static List<FoodSpec> findAllFoodSpec() {
        return DataSupport.findAll(FoodSpec.class);
    }

    //-----------菜品-----------



}
