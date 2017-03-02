package com.mpyf.lening.interfaces.bean.Result;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DealResult {


/**
*处理结果
*/
@JsonProperty("Result")
    private Boolean result;
    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean val) {
        result = val;
    }

/**
*消息
*/
@JsonProperty("Message")
    private String message;
    public String getMessage() {
        return message;
    }

    public void setMessage(String val) {
        message = val;
    }

/**
*关联数据
*/
@JsonProperty("Data")
    private Object data;
    public Object getData() {
        return data;
    }

    public void setData(Object val) {
        data = val;
    }

            
}
