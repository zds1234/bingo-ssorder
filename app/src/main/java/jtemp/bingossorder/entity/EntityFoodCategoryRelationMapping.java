package jtemp.bingossorder.entity;

import org.litepal.crud.DataSupport;

/**
 * Created by ZMS on 2016/11/23.
 */

public class EntityFoodCategoryRelationMapping extends DataSupport {
    private int id;

    private int categoryId;

    private int relationCategoryId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getRelationCategoryId() {
        return relationCategoryId;
    }

    public void setRelationCategoryId(int relationCategoryId) {
        this.relationCategoryId = relationCategoryId;
    }
}
