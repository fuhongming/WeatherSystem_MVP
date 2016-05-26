package com.iotek.weathersystem.model;

import java.util.List;

/**
 * Created by fhm on 2016/5/26.
 */
public class Result {
    private Sk sk;

    private Today today;

    private List<Future> future ;

    public void setSk(Sk sk){
        this.sk = sk;
    }
    public Sk getSk(){
        return this.sk;
    }
    public void setToday(Today today){
        this.today = today;
    }
    public Today getToday(){
        return this.today;
    }
    public void setFuture(List<Future> future){
        this.future = future;
    }
    public List<Future> getFuture(){
        return this.future;
    }

}
