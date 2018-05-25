package com.practice.third.tx;

import com.alibaba.fastjson.JSONObject;
import com.practice.util.IOUtils;
import com.practice.util.KeyUtils;
import com.practice.util.MapKeyComparator;
import com.practice.util.TxUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.nio.charset.Charset;
import java.util.TreeMap;

/**
 * @author lxl
 * @Date 2018/5/24 10:34
 */
public class ImgClassify {
    private final static Logger log = LoggerFactory.getLogger(ImgClassify.class);
    private static final String app_id = "1106927758";
    private static final String app_key = "";
    private static final String url = "https://api.ai.qq.com/fcgi-bin/ocr/ocr_generalocr";

    public static void main(String[] args) throws Exception{
        TreeMap<String,String> sortMap = new TreeMap<>(new MapKeyComparator());
        sortMap.put("app_id",app_id);
        sortMap.put("time_stamp",String.valueOf(System.currentTimeMillis()/1000));
        sortMap.put("nonce_str", KeyUtils.randomStr2(6));
        String base64Img = new BASE64Encoder().encode(IOUtils.readBytes(new File("D:\\workspace\\temp\\menu.saveimg.savepath20180515185214.jpg")));
        sortMap.put("image",base64Img);
        String reqSign = TxUtils.getReqSign(sortMap, app_key);
        sortMap.put("sign", reqSign);

        String body = JSONObject.toJSONString(sortMap);
        System.out.println("tst " + body);

        HttpPost post = null;
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            post = new HttpPost(url);
            post.setHeader("Content-type", "application/json; charset=utf-8");

            StringEntity entity = new StringEntity(body, Charset.forName("UTF-8"));
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            post.setEntity(entity);

            HttpResponse response = httpclient.execute(post);

            int statusCode = response.getStatusLine().getStatusCode();
            if(statusCode != HttpStatus.SC_OK){
                log.info("error request");
            }else{
                HttpEntity responseEntity = response.getEntity();
                String jsonString = EntityUtils.toString(responseEntity);
                log.info(jsonString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(post != null){
                try {
                    post.releaseConnection();
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

//        RequestBuilder builder = new RequestBuilder();
//        builder.setMethod("POST").setBody(body).setUrl(url)
//                .setHeader("Content-type","application/json; charset=utf-8");
//        AsyncHttpClient client = asyncHttpClient();
//        client.executeRequest(builder).toCompletableFuture()
//                .thenApply(Response::getResponseBody)
//                .thenAccept(System.out::println)
//                .join();
    }


}
