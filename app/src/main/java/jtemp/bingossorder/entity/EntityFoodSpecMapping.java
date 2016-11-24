package jtemp.bingossorder.entity;

import org.litepal.crud.DataSupport;

/**
 * Created by ZMS on 2016/11/23.
 */

public class EntityFoodSpecMapping extends DataSupport {

    int id;

    int foodId;

    int foodSpecId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public int getFoodSpecId() {
        return foodSpecId;
    }

    public void setFoodSpecId(int foodSpecId) {
        this.foodSpecId = foodSpecId;
    }
}
