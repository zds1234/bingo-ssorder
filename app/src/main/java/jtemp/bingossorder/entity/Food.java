package jtemp.bingossorder.entity;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by ZMS on 2016/11/23.
 * 菜品
 */

public class Food extends DataSupport {

    @Column(nullable = false, unique = true)
    private long id;

    /**
     * 类别
     */
    private int categoryId;

    /**
     * 名称
     */
    private String name;
    private String nameEn;

    /**
     * 价格
     */
    private double price;

    /**
     * 是否推荐
     */
    private boolean recommend;

    /**
     * 是否上架
     */
    private boolean saleable;

    /**
     * 图像
     */
    private String image;

    /**
     * 关联菜品
     */
    private String relations;

    /**
     * 菜品规格
     */
    private String spec;

    //关联
    @Column(ignore = true)
    private FoodCategory foodCategory;

    /**
     * 关联菜品
     */
    @Column(ignore = true)
    private List<Food> foodRelations;

    /**
     * 菜品规格
     */
    @Column(ignore = true)
    private List<FoodSpec> foodSpec;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRelations() {
        return relations;
    }

    public void setRelations(String relations) {
        this.relations = relations;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public FoodCategory getFoodCategory() {
        return foodCategory;
    }

    public void setFoodCategory(FoodCategory foodCategory) {
        this.foodCategory = foodCategory;
    }

    public List<Food> getFoodRelations() {
        return foodRelations;
    }

    public void setFoodRelations(List<Food> foodRelations) {
        this.foodRelations = foodRelations;
    }

    public List<FoodSpec> getFoodSpec() {
        return foodSpec;
    }

    public void setFoodSpec(List<FoodSpec> foodSpec) {
        this.foodSpec = foodSpec;
    }
}
