package jtemp.bingossorder.entity;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by ZMS on 2016/11/23.
 */

public class EntityFood extends DataSupport {

    @Column(nullable = false, unique = true)
    int id;

    @Column(ignore = true)
    EntityFoodCategory entityFoodCategory;
    int category_id;
    String name;
    String nameEn;
    double price;
    boolean recommend;
    boolean saleable;
    byte[] image;

    @Column(ignore = true)
    List<EntityFood> relations;

    @Column(ignore = true)
    List<EntityFoodSpec> specList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EntityFoodCategory getEntityFoodCategory() {
        return entityFoodCategory;
    }

    public void setEntityFoodCategory(EntityFoodCategory entityFoodCategory) {
        this.entityFoodCategory = entityFoodCategory;
        setCategory_id(entityFoodCategory.getId());
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public boolean isSaleable() {
        return saleable;
    }

    public void setSaleable(boolean saleable) {
        this.saleable = saleable;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public List<EntityFood> getRelations() {
        return relations;
    }

    public void setRelations(List<EntityFood> relations) {
        this.relations = relations;
    }

    public List<EntityFoodSpec> getSpecList() {
        return specList;
    }

    public void setSpecList(List<EntityFoodSpec> specList) {
        this.specList = specList;
    }
}
