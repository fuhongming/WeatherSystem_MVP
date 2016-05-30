package com.iotek.weathersystem.utils;

import android.os.Environment;

import com.iotek.weathersystem.commom.Canstants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

/**
 * Created by fhm on 2016/5/29.
 */
public class FileUtils {
    public static void save(String jsonStr, String fileName) {
        if (isMounted()) {
            File f = new File(Canstants.CACHE_PATH);
            if (!f.exists()) {
                f.mkdirs();
            }
            File file = new File(Canstants.CACHE_PATH, fileName);
            try {
                FileOutputStream fos = new FileOutputStream(file);

                fos.write(jsonStr.getBytes());
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String read(String fileName) {
        //获得SD卡对应的存储目录
        File file = new File(Canstants.CACHE_PATH, fileName);
        //获取指定文件对应的输入流
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            //将指定输入流包装成BufferReader
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            StringBuffer sb = new StringBuffer("");
            String line = null;
            //循环读取文件内容
            while((line = br.readLine()) != null){
                sb.append(line);
            }
            br.close();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static boolean isMounted() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
}
