package com.navercorp.pinpoint.web.alarm;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;



public class HttpUtil {
    /*public static void main(String[] args) {
        JSONObject object = new JSONObject();
        try {
            object.put("msgtype", "text");
            JSONObject text = new JSONObject();
            text.put("content", "tf test");
            object.put("text", text);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        for(int i = 20;i>=0;i--) {
            doPost("https://oapi.dingtalk.com/robot/send?access_token=b39e9d52b898af22bdecdf5adc01724961513281cca86b28bd7ad0579bf7649f", object);
        }
    }*/
    public static String doPost(String url, JSONObject object) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        String result = "";
        // 创建httpClient实例
        httpClient = HttpClients.createDefault();
        // 创建httpPost远程连接实例
        HttpPost httpPost = new HttpPost(url);
        // 配置请求参数实例
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(2500)// 设置连接主机服务超时时间
                .setConnectionRequestTimeout(2000)// 设置连接请求超时时间
                .setSocketTimeout(3000)// 设置读取数据连接超时时间
                .build();
        // 为httpPost实例设置配置
        httpPost.setConfig(requestConfig);
        // 设置请求头
        httpPost.addHeader("Content-Type", "application/json");
        StringEntity entity1 = new StringEntity(object.toString(),"utf-8");
        entity1.setContentType("application/json");;
        httpPost.setEntity(entity1);
        try {
            // httpClient对象执行post请求,并返回响应参数对象
            httpResponse = httpClient.execute(httpPost);
            // 从响应对象中获取响应内容
            HttpEntity entity = httpResponse.getEntity();
            result = EntityUtils.toString(entity);
            System.out.println(result);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != httpResponse) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}