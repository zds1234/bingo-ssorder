package jtemp.bingossorder.entity;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by ZMS on 2016/11/23.
 * 菜品规格
 */

public class FoodSpec extends DataSupport {

    @Column(nullable = false, unique = true)
    private long id;

    /**
     * 规格类别
     */
    @Column(nullable = false)
    private String specType;

    /**
     * 规格名称
     */
    @Column(nullable = true, unique = true)
    private String specName;

    private String ext;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }
}
