package jtemp.bingossorder.entity.test;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by ZMS on 2016/11/30.
 */

public class Foo extends DataSupport {

    private long id;

    private String text;

    private List<Bar> bars;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Bar> getBars() {
        return bars;
    }

    public void setBars(List<Bar> bars) {
        this.bars = bars;
    }
}
