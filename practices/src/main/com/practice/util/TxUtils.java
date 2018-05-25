package com.practice.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author lxl
 * @Date 2018/5/24 10:48
 */
public class TxUtils {
    /**
     * 腾讯接口鉴权算法
     * @param params
     * @param appKey
     * @return
     */
    public static String getReqSign(TreeMap<String,String> params,String appKey){
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String,String> entry:params.entrySet()){
            try {
                sb.append(entry.getKey()).append("=")
                        .append(URLEncoder.encode(String.valueOf(entry.getValue()),"utf-8"))
                        .append("&");
            } catch (UnsupportedEncodingException e) {
                //
            }
        }

        sb.append("app_key=").append(appKey);

        return DigestUtils.md5(sb.toString(),null).toUpperCase();
    }
    /**
     * 腾讯接口鉴权算法
     * @param params
     * @param appKey
     * @return
     */
    public static String getReqSign(Map<String,Object> params,String appKey){
        Map<String,Object> sortMap = new TreeMap<String,Object>(new MapKeyComparator());
        sortMap.putAll(params);

        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String,Object> entry:sortMap.entrySet()){
            try {
                sb.append(entry.getKey()).append("=")
                        .append(URLEncoder.encode(String.valueOf(entry.getValue()),"UTF-8"))
                        .append("&");
            } catch (UnsupportedEncodingException e) {
                //跳过
            }
        }

        sb.append("app_key=").append(appKey);

        return sb.toString();
    }

}
