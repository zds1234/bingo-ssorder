package jtemp.bingossorder.entity;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZMS on 2016/11/22.
 * 菜品类别
 */

public class FoodCategory extends DataSupport {

    @Column(nullable = false, unique = true)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    /**
     * 单品限购 0为不限购
     */
    private int purchaseLimit;

    /**
     * 排序
     */
    private int sort;

    /**
     * 是否套餐
     */
    private boolean combo;

    /**
     * 关联菜品类别
     */
    private String associated;

    @Column(ignore = true)
    private List<FoodCategory> associatedCategory = new ArrayList<>();

    private String ext;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPurchaseLimit() {
        return purchaseLimit;
    }

    public void setPurchaseLimit(int purchaseLimit) {
        this.purchaseLimit = purchaseLimit;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public boolean isCombo() {
        return combo;
    }

    public void setCombo(boolean combo) {
        this.combo = combo;
    }

    public String getAssociated() {
        return associated;
    }

    public void setAssociated(String associated) {
        this.associated = associated;
    }

    public List<FoodCategory> getAssociatedCategory() {
        return associatedCategory;
    }

    public void setAssociatedCategory(List<FoodCategory> associatedCategory) {
        this.associatedCategory = associatedCategory;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }
}
