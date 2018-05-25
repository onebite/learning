package com.practice.io;

import com.baidu.aip.imageclassify.AipImageClassify;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * @author lxl
 * @Date 2018/5/15 18:23
 */
public class BaiduImgClassify {
    private static final String app_id = "11245305";
    private static final String app_key = "djghwQcb1rnfYKN8h2LSjyzO";
    private static final String secret_key = "";

    public static void main(String[] args) throws Exception{
        AipImageClassify client = new AipImageClassify(app_id,app_key,secret_key);
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("top_num", "13");

        JSONObject jsonObject = client.dishDetect("D:\\workspace\\temp\\menu.saveimg.savepath20180515185214.jpg",options);
        System.out.println(jsonObject.toString(2));
    }
}
