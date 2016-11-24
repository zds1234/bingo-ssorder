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
}
