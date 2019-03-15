/*
 * Copyright 2014 NAVER Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.navercorp.pinpoint.web.alarm;

import org.codehaus.jettison.json.JSONObject;

import com.navercorp.pinpoint.web.alarm.checker.AlarmChecker;
import org.codehaus.jettison.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @author minwoo.jung
 */
public class EmptyMessageSender implements AlarmMessageSender {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
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
        HttpUtil.doPost("https://oapi.dingtalk.com/robot/send?access_token=b39e9d52b898af22bdecdf5adc01724961513281cca86b28bd7ad0579bf7649f", object);
        logger.info("发送钉钉end");
        //钉钉代码块end
    }

    @Override
    public void sendEmail(AlarmChecker checker, int sequenceCount) {
    }

}
