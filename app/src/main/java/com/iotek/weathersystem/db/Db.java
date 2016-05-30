package com.iotek.weathersystem.db;

import com.iotek.weathersystem.model.City;
import com.iotek.weathersystem.utils.Tools;

import org.xutils.DbManager;
import org.xutils.db.table.DbModel;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.List;


/**
 * Created by fhm on 2016/5/28.
 * 对SQLite数据库操作工具类
 */

public class Db {
    static DbManager.DaoConfig daoConfig = new DbManager.DaoConfig().setDbName("weather.db");
    static String temp = "";

    private static DbManager dbManager = x.getDb(daoConfig);

    /**
     * 保存城市信息到数据库
     * @param city
     */
    public static void save(City city) {

        try {
            dbManager.save(city);
        } catch (Throwable e) {
            temp += "error :" + e.getMessage() + "\n";
        }
    }

    /**
     * 删除数据库中的城市
     * @param id
     */
    public static void del(int id) {

        try {
            dbManager.deleteById(City.class, id);
        } catch (Throwable e) {
            temp += "error :" + e.getMessage() + "\n";
        }
    }

    /**
     * 查询城市信息
     * @return
     */
    public static List<City> select() {
        List<City> citys = null;
        try {
            citys = dbManager.selector(City.class).findAll();
            return citys;
        } catch (Throwable e) {
            temp += "error :" + e.getMessage() + "\n";
        }
        return  citys;
    }



    public void s() {
        DbManager db = x.getDb(daoConfig);
        try {

            City test = db.selector(City.class).where("id", "in", new int[]{1, 3, 6}).findFirst();
            if (test != null) {
                Tools.log(test.getName());
            }

            List<City> list = db.selector(City.class)
                    .where("id", "<", 54)
                    .orderBy("id")
                    .limit(10).findAll();

            List<DbModel> dbModels = db.selector(City.class)
                    .groupBy("name")
                    .select("name", "count(name) as count").findAll();
            temp += "group by result:" + dbModels.get(0).getDataMap() + "\n";

        } catch (DbException e) {
            e.printStackTrace();
        }

    }
}
