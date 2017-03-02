package com.mpyf.lening.interfaces.http;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

/**
 * Created by Administrator on 2015/12/23.
 */
public class DealResult extends PropertyNamingStrategy {
    //限定属属性名称
    @JsonProperty("Result")
    private Boolean Result;
    @JsonProperty("Message")
    private String Message;
    @JsonProperty("Data")//关联数据
    private Object Data;

    public Boolean getResult() {
        return Result;
    }

    public void setResult(Boolean result) {
        this.Result = result;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        this.Message = message;
    }

    public Object getData() {
        return Data;
    }

    public void setData(Object data) {
        this.Data = data;
    }

    public static String toUpperCaseFirstOne(String s)
    {
        if(Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    public<T> T  deserializeData(Class<T> clazz)throws Exception {
        T result = JsonUtils.deserialize(Data.toString(), clazz);
        return result;
    }
    public<T> List deserializeListData(Class<T> clazz)throws Exception {
        List<?> result = JsonUtils.deserializeList(Data.toString(), clazz);
        return result;
    }
}
