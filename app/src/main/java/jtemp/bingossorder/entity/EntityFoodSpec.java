package jtemp.bingossorder.entity;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by ZMS on 2016/11/23.
 */

public class EntityFoodSpec extends DataSupport {

    @Column(nullable = false, unique = true)
    private int id;

    @Column(nullable = false)
    private String specType;

    @Column(nullable = true, unique = true)
    private String specName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSpecType() {
        return specType;
    }

    public void setSpecType(String specType) {
        this.specType = specType;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }
}
