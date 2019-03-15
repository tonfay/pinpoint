package com.navercorp.pinpoint.web.alarm.mail;

import java.security.GeneralSecurityException;
import java.util.Properties;

import com.sun.mail.util.MailSSLSocketFactory;

public class MailSenderInfo {
    // 发送邮件的服务器的IP和端口   
    private String mailServerHost;   
    private String mailServerPort = "25";   
    // 邮件发送者的地址   
    private String fromAddress;   
    // 邮件接收者的地址   
    private String toAddress;   
    // 登陆邮件发送服务器的用户名和密码   
    private String userName;   
    private String password;   
    // 是否需要身份验证   
    private boolean validate = false;   
    // 邮件主题   
    private String subject;   
    // 邮件的文本内容   
    private String content;   
    // 邮件附件的文件名   
    private String[] attachFileNames;
    
    public Properties getProperties(){   
        Properties p = new Properties();   
        p.setProperty("mail.transport.protocol", "smtp");
        p.put("mail.smtp.host", this.mailServerHost);   
        p.put("mail.smtp.port", this.mailServerPort);   
        p.put("mail.smtp.auth", validate ? "true" : "false");   
        p.put("mail.smtp.ssl.enable", "true");
        //使用SSL，企业邮箱必需！
        //开启安全协议
        MailSSLSocketFactory sf = null;
        try {
            sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
        } catch (GeneralSecurityException e1) {
            e1.printStackTrace();
        }
        p.put("mail.smtp.ssl.socketFactory", sf);
        return p;   
      }   
    public String getMailServerHost() {   
      return mailServerHost;   
    }   
    public void setMailServerHost(String mailServerHost) {   
      this.mailServerHost = mailServerHost;   
    }  
    public String getMailServerPort() {   
      return mailServerPort;   
    }  
    public void setMailServerPort(String mailServerPort) {   
      this.mailServerPort = mailServerPort;   
    }  
    public boolean isValidate() {   
      return validate;   
    }  
    public void setValidate(boolean validate) {   
      this.validate = validate;   
    }  
    public String[] getAttachFileNames() {   
      return attachFileNames;   
    }  
    public void setAttachFileNames(String[] fileNames) {   
      this.attachFileNames = fileNames;   
    }  
    public String getFromAddress() {   
      return fromAddress;   
    }   
    public void setFromAddress(String fromAddress) {   
      this.fromAddress = fromAddress;   
    }  
    public String getPassword() {   
      return password;   
    }  
    public void setPassword(String password) {   
      this.password = password;   
    }  
    public String getToAddress() {   
      return toAddress;   
    }   
    public void setToAddress(String toAddress) {   
      this.toAddress = toAddress;   
    }   
    public String getUserName() {   
      return userName;   
    }  
    public void setUserName(String userName) {   
      this.userName = userName;   
    }  
    public String getSubject() {   
      return subject;   
    }  
    public void setSubject(String subject) {   
      this.subject = subject;   
    }
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
    
}