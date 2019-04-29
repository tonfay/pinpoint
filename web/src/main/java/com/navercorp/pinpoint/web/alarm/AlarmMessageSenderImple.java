package com.navercorp.pinpoint.web.alarm;

import java.util.List;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.navercorp.pinpoint.web.alarm.checker.AlarmChecker;
import com.navercorp.pinpoint.web.alarm.mail.MailSenderInfo;
import com.navercorp.pinpoint.web.alarm.mail.SimpleMailSender;
import com.navercorp.pinpoint.web.service.UserGroupService;

public class AlarmMessageSenderImple implements AlarmMessageSender{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
     
    @Autowired
    private UserGroupService userGroupService;
    @Override
    public void sendSms(AlarmChecker checker, int sequenceCount) {
        //钉钉代码块begin
        JSONObject object = new JSONObject();
        try {
            object.put("msgtype", "text");
            JSONObject text = new JSONObject();
            String msg = checker.getEmailMessage();
            msg = msg.replaceAll("<br>", "\n");
            text.put("content", msg);//这里用email的邮件内容即可
            object.put("text", text);
        } catch (JSONException e) {
            logger.error("组装参数异常",e);
        }
        logger.info("发送钉钉begin");
        HttpUtil.doPost("https://oapi.dingtalk.com/robot/send?access_token=a3c4111fb4fe0550c74407892b8bb317b8faa1db35909765b71a87cd0cef7ad9", object);
        logger.info("发送钉钉end");
        //钉钉代码块end
        
        /*List<String> receivers = userGroupService.selectPhoneNumberOfMember(checker.getuserGroupId());
         
        if (receivers.size() == 0) {
            return;
        }
 
        List<String> sms = checker.getSmsMessage();
        for (String message : sms) {
            logger.info("send SMS : {}", message);
            
 
        }*/
    }

    @Override
    public void sendEmail(AlarmChecker checker, int sequenceCount) {
        List<String> receivers = userGroupService.selectEmailOfMember(checker.getuserGroupId());
        logger.info("当前需发送人数=>[{}]" + receivers.size());
        if (receivers.size() == 0) {
            return;
        }
 
        for (String emailId : receivers) {
 
            /*  这个类主要是设置邮件  
             *  mailServerHost:邮箱服务器
             *  userName:发送方邮件用户名
             *  password:发送方邮件密码
             *  fromAddress:发送方邮件用户名
             *  toAddress:接收方邮件用户名
             */
            MailSenderInfo mailInfo = new MailSenderInfo();
            mailInfo.setMailServerHost("smtp.exmail.qq.com");
            mailInfo.setMailServerPort("465");
            mailInfo.setValidate(true);
            mailInfo.setUserName("techwarning@shouqiev.com");
            mailInfo.setPassword("827@!eovGeY3PHN");
            mailInfo.setFromAddress("techwarning@shouqiev.com");
            mailInfo.setToAddress(emailId);
            mailInfo.setSubject("PP WARN");
            mailInfo.setContent(checker.getEmailMessage());
            // 这个类主要来发送邮件
            SimpleMailSender sms = new SimpleMailSender();
            boolean con = false;
 
            try {
                con = sms.sendHtmlMail(mailInfo);// 发送文体格式
            } catch (Exception e) {
                logger.error("=AlarmMessageSenderImple.sendEmail=>错误原因是:" + e.getMessage(),e);
            }
            if (con) {
                logger.info("=AlarmMessageSenderImple.sendEmail=>"+emailId+"发送成功！邮件内容："+checker.getEmailMessage());
            } else {
                logger.info("=AlarmMessageSenderImple.sendEmail=>"+emailId+"发送失败！邮件内容："+checker.getEmailMessage());
            }
        }

    }

}