package jtemp.bingossorder.code;

/**
 * Created by ZMS on 2016/11/23.
 */

public enum FoodSpecType {
    SIZE("规格"),
    FLAVOUR("口味"),
    AVOID("忌口");

    private String name;

    FoodSpecType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
