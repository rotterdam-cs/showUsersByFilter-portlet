package com.rcs.common;

import com.google.gson.Gson;

/**
 *
 * @author chuqui
 */
public class JSONResponse {

    private boolean success = true;
    private String message = "";
    private Object data = null;
    private Object[] dataArray = null;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object[] getDataArray() {
        return dataArray;
    }

    public void setDataArray(Object[] dataArray) {
        this.dataArray = dataArray;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    @Override
    public String toString() {
        Gson gson = new Gson(); 
        return gson.toJson(this);
    }
}
