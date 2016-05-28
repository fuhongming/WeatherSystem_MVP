package com.iotek.weathersystem.model;

import java.util.List;

/**
 * Created by fhm on 2016/5/28.
 */
public class Root {
    private String success;

    private List<Result> result ;

    public void setSuccess(String success){
        this.success = success;
    }
    public String getSuccess(){
        return this.success;
    }
    public void setResult(List<Result> result){
        this.result = result;
    }
    public List<Result> getResult(){
        return this.result;
    }

}
