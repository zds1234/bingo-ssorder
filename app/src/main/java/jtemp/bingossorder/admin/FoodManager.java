package jtemp.bingossorder.admin;

import org.litepal.crud.DataSupport;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import jtemp.bingossorder.code.ErrorCode;
import jtemp.bingossorder.code.FoodSpecType;

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
     * @param associations  关联菜品
     * @return
     */
    public static ErrorCode addFoodCategory(String name,
                                            int purchaseLimit,
                                            int sort,
                                            boolean combo,
                                            FoodCategory... associations) {

        if (DataSupport.isExist(FoodCategory.class, "name=?", name)) {
            return ErrorCode.FOOD_CATEGORY_EXISTS;
        }
        FoodCategory foodCategory = createFoodCategory(name, purchaseLimit, sort, combo, associations);
        foodCategory.save();
        return ErrorCode.SUCCESS;
    }

    private static FoodCategory createFoodCategory(String name, int purchaseLimit, int sort, boolean combo, FoodCategory... associations) {
        FoodCategory category = new FoodCategory();
        category.setName(name);
        category.setPurchaseLimit(purchaseLimit);
        category.setSort(sort);
        category.setCombo(combo);
        if (category.isCombo()) {
            StringBuilder sb = new StringBuilder();
            category.setAssociatedCategory(Arrays.asList(associations));
            for (FoodCategory c : associations) {
                sb.append(c.getId()).append(";");
            }
            category.setAssociated(sb.toString());
        }
        return category;
    }

    /**
     * 删除菜品类别，会同时将该类别下的所有菜品删除
     *
     * @param id
     * @return
     */
    public static ErrorCode deleteFoodCategory(long id) {
        DataSupport.delete(FoodCategory.class, id);
        DataSupport.deleteAll(Food.class, "categoryId=?", String.valueOf(id));
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
            parseFoodCategoryAssociations(category);
        }
        return list;
    }

    private static void parseFoodCategoryAssociations(FoodCategory category) {
        if (category != null && category.isCombo()) {
            String[] array = category.getAssociated().split(";");
            long[] ids = new long[array.length];
            for (int i = 0; i < array.length; i++) {
                ids[i] = Long.parseLong(array[i]);
            }
            List<FoodCategory> associatedCategory = FoodCategory.findAll(FoodCategory.class, ids);
            for (Iterator<FoodCategory> it = associatedCategory.iterator(); it.hasNext(); ) {
                FoodCategory c = it.next();
                if (c == null || c.isCombo()) {
                    it.remove();
                }
            }
            category.setAssociatedCategory(associatedCategory);
        }
    }

    /**
     * 查找菜品类别
     *
     * @param id
     * @return
     */
    public static FoodCategory findFoodCategory(long id) {
        FoodCategory category = DataSupport.find(FoodCategory.class, id);
        parseFoodCategoryAssociations(category);
        return category;
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

    /**
     * 添加食品
     *
     * @param foodCategory
     * @param name
     * @param nameEn
     * @param price
     * @param recommend
     * @param saleable
     * @param image
     * @param foodAssociations
     * @param foodSpec
     * @return
     */
    public static ErrorCode addFood(FoodCategory foodCategory,
                                    String name,
                                    String nameEn,
                                    double price,
                                    boolean recommend,
                                    boolean saleable,
                                    String image,
                                    List<Food> foodAssociations,
                                    List<FoodSpec> foodSpec) {

        if (foodCategory.isCombo() && foodAssociations == null || foodAssociations.isEmpty()) {
            return ErrorCode.FOOD_PLS_CHOOSE_RELATION;
        }

        Food food = createFood(foodCategory, name, nameEn, price, recommend, saleable, image, foodAssociations, foodSpec);
        food.save();
        return ErrorCode.SUCCESS;
    }

    private static Food createFood(FoodCategory foodCategory, String name, String nameEn, double price, boolean recommend, boolean saleable, String image, List<Food> foodAssociations, List<FoodSpec> foodSpec) {
        Food food = new Food();
        food.setFoodCategory(foodCategory);
        food.setCategoryId(foodCategory.getId());
        food.setName(name);
        food.setNameEn(nameEn);
        food.setPrice(price);
        food.setRecommend(recommend);
        food.setSaleable(saleable);
        food.setImage(image);
        if (foodCategory.isCombo()) {
            StringBuilder associated = new StringBuilder();
            for (Food f : foodAssociations) {
                associated.append(f.getId()).append(";");
            }
            foodCategory.setAssociated(associated.toString());
            food.setFoodAssociated(foodAssociations);
        }
        if (foodSpec != null && !foodSpec.isEmpty()) {
            StringBuilder str = new StringBuilder();
            for (FoodSpec spec : foodSpec) {
                str.append(spec.getId()).append(";");
            }
            food.setSpec(str.toString());
            food.setFoodSpec(foodSpec);
        }
        return food;
    }

    /**
     * 更新菜品
     *
     * @param id
     * @param foodCategory
     * @param name
     * @param nameEn
     * @param price
     * @param recommend
     * @param saleable
     * @param image
     * @param foodRelations
     * @param foodSpec
     * @return
     */
    public static ErrorCode updateFood(long id,
                                       FoodCategory foodCategory,
                                       String name,
                                       String nameEn,
                                       double price,
                                       boolean recommend,
                                       boolean saleable,
                                       String image,
                                       List<Food> foodRelations,
                                       List<FoodSpec> foodSpec) {

        if (foodCategory.isCombo() && foodRelations == null || foodRelations.isEmpty()) {
            return ErrorCode.FOOD_PLS_CHOOSE_RELATION;
        }
        Food food = createFood(foodCategory, name, nameEn, price, recommend, saleable, image, foodRelations, foodSpec);
        food.update(id);
        return ErrorCode.SUCCESS;
    }

    /**
     * 查找所有菜品
     *
     * @return
     */
    public static List<Food> findAllFood() {
        List<Food> list = DataSupport.findAll(Food.class);
        for (Iterator<Food> it = list.iterator(); it.hasNext(); ) {
            Food food = it.next();
            if (!parseFoodAssociations(food)) {
                it.remove();
            }
        }
        return list;
    }

    private static boolean parseFoodAssociations(Food food) {
        FoodCategory category = findFoodCategory(food.getCategoryId());
        if (category == null) {
            return false;
        }
        food.setFoodCategory(category);
        return true;
    }

}
