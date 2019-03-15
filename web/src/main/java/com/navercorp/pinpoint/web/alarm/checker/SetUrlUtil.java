package com.navercorp.pinpoint.web.alarm.checker;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.navercorp.pinpoint.web.alarm.vo.Rule;

/**
 * 邮件中增加过去五分钟的url
 * @author LENOVO
 *
 */
public class SetUrlUtil {
    public static String SERVER_HOST = "http://10.18.105.245:8031";
    public static String get(Rule rule) {
        String applicationId = rule.getApplicationId();
        String serverType = rule.getServiceType();
        StringBuffer url = new StringBuffer();
        url.append(SERVER_HOST);
        url.append("/#/main/");
        url.append(applicationId);
        url.append("@");
        url.append(serverType);
        url.append("/5m/");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String nowdate = sdf.format(new Date((System.currentTimeMillis())));
        url.append(nowdate);
        url.append("<br>");
        return url.toString();
    }
    
    public static String getAgentUrl(Rule rule,String detected) {
//        http://10.18.105.245:8031/#/inspector/ms-order-service@SPRING_BOOT/5m/2018-09-11-19-41-52/ms-order-service-105.237
        
        String applicationId = rule.getApplicationId();
        String serverType = rule.getServiceType();
        StringBuffer url = new StringBuffer();
        url.append(SERVER_HOST);
        url.append("/#/inspector/");
        url.append(applicationId);
        url.append("@");
        url.append(serverType);
        url.append("/5m/");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String nowdate = sdf.format(new Date((System.currentTimeMillis())));
        url.append(nowdate);
        url.append("/");
        url.append(detected);
        return url.toString();
    }
}