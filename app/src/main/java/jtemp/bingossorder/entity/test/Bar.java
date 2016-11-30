package jtemp.bingossorder.entity.test;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by ZMS on 2016/11/30.
 */

public class Bar extends DataSupport {
    private long id;

    private String text;

    private List<Foo> foos;

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

    public List<Foo> getFoos() {
        return foos;
    }

    public void setFoos(List<Foo> foos) {
        this.foos = foos;
    }
}
