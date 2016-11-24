package jtemp.bingossorder.entity;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by ZMS on 2016/11/22.
 */

public class EntityFoodCategory extends DataSupport {

    @Column(nullable = false, unique = true)
    private int id;

    @Column(nullable = false, unique = true)
    private String categoryName;

    private int purchaseLimit;

    private int categoryOrder;

    private boolean taocan;

    @Column(ignore = true)
    private List<EntityFoodCategory> relationCategory;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getPurchaseLimit() {
        return purchaseLimit;
    }

    public void setPurchaseLimit(int purchaseLimit) {
        this.purchaseLimit = purchaseLimit;
    }

    public int getCategoryOrder() {
        return categoryOrder;
    }

    public void setCategoryOrder(int categoryOrder) {
        this.categoryOrder = categoryOrder;
    }

    public boolean isTaocan() {
        return taocan;
    }

    public void setTaocan(boolean taocan) {
        this.taocan = taocan;
    }

    public List<EntityFoodCategory> getRelationCategory() {
        return relationCategory;
    }

    public void setRelationCategory(List<EntityFoodCategory> relationCategory) {
        this.relationCategory = relationCategory;
    }
}
