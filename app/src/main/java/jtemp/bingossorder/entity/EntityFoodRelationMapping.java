package jtemp.bingossorder.entity;

import org.litepal.crud.DataSupport;

/**
 * Created by ZMS on 2016/11/23.
 */

public class EntityFoodRelationMapping extends DataSupport {
    int id;
    int foodId;
    int foodRelationId;

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

    public int getFoodRelationId() {
        return foodRelationId;
    }

    public void setFoodRelationId(int foodRelationId) {
        this.foodRelationId = foodRelationId;
    }
}
