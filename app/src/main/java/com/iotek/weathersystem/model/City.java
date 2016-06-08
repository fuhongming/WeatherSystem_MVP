package com.iotek.weathersystem.model;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;


@Table(name = "city")
public class City {
    @Column(name = "id", isId = true)
    public int id;

    @Column(name = "name")
    private String name;

    private String pinyin;

    public City() {
    }

    public City(String name, String pinyin) {
        this.name = name;
        this.pinyin = pinyin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }
}
