package com.paulanussbrand.store.dto;

import java.util.ArrayList;
import java.util.List;

public class ResponseApi {
    private Integer status;
    private List<Object> data = new ArrayList<>();
    private String message;
    private List<Object> meta = new ArrayList<>();

    public ResponseApi(){}
    public ResponseApi(Integer status, List<Object> data, String message, List<Object> meta) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.meta = meta;
    }

    public ResponseApi(Integer status, Object dataToReturn, String message, Object metaDataToReturn){
        setStatus(status);
        getData().add(dataToReturn);
        setMessage(message);
        addMetaData(metaDataToReturn);
    }

    public void addMetaData(Object addMetaDataToReturn){
        getMeta().add(addMetaDataToReturn);
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public List<Object> getData() {
        return data;
    }
    public void setData(List<Object> data) {
        this.data = data;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public List<Object> getMeta() {
        return meta;
    }
    public void setMeta(List<Object> meta) {
        this.meta = meta;
    }
}
