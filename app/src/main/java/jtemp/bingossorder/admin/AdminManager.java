package jtemp.bingossorder.admin;

import jtemp.bingossorder.entity.EntityFoodCategory;

/**
 * Created by ZMS on 2016/11/21.
 * <p>
 * 管理员管理
 */

public final class AdminManager {

    /**
     * 密码校验
     *
     * @param password
     * @return
     */
    public static boolean isAdminPasswordValidate(String password) {
        return "1".equals(password);
    }
}
