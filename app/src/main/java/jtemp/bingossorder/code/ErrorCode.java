package jtemp.bingossorder.code;

/**
 * Created by ZMS on 2016/11/27.
 */

public enum ErrorCode {

    SUCCESS(0, "success"),

    FOOD_CATEGORY_EXISTS(1, "菜品类别已存在"),
    FOOD_PLS_CHOOSE_RELATION(2, "请选择关联菜品"),

    //
    ;

    private int code;

    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return code == 0;
    }
}
