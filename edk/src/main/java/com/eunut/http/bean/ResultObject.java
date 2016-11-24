package com.eunut.http.bean;

import java.util.HashMap;
import java.util.Map;

public class ResultObject<T> {
    protected ResultCode code = ResultCode.FAIL;// 0:成功；1:失败
    protected String msg;// 提示信息，一般是在失败时才有，成功是返回Null
    protected T data = null;// 请求成功是返回的数据
    protected Map<String, Object> attributes = null;// 附属属性信息

    /**
     * 添加额外属性信息，以Map的方式呈现
     *
     * @param key   外信息的key
     * @param value 外信息的value
     */
    public void addAttribute(String key, Object value) {
        if (attributes == null) {
            attributes = new HashMap<String, Object>();
        }
        attributes.put(key, value);
    }

    /**
     * 添加多个额外的属性
     *
     * @param values 以map形式存在的属性值
     */
    public void addAttribute(Map<String, Object> values) {
        if (attributes == null) {
            attributes = new HashMap<String, Object>();
        }
        attributes.putAll(values);
    }

    /**
     * 重设额外信息集合，并置空attributes
     */
    public void clearAttribute() {
        if (attributes != null) {
            attributes.clear();
            attributes = null;
        }
    }

    public ResultCode getCode() {
        return code;
    }

    public void setCode(ResultCode code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

}
