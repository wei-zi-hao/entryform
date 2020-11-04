package com.ek.project.system.form.service.impl;

import com.ek.common.utils.IpUtils;
import com.ek.common.utils.MailUtil;
import com.ek.common.utils.NoteUtil;
import com.ek.common.utils.StringUtils;
import com.ek.project.system.form.domain.FormInfo;
import com.ek.project.system.form.mapper.FormDataMapper;
import com.ek.project.system.form.service.IFormDataService;
import com.ek.project.system.mail.domain.Mail;
import com.ek.project.system.mail.mapper.MailMapper;
import com.ek.project.system.mailConfig.domain.MailConfig;
import com.ek.project.system.mailConfig.mapper.MailConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 报名表Service业务层处理
 *
 * @author eric
 * @date 2020-08-28
 */
@Service
public class FormDataServiceImpl implements IFormDataService {
    @Autowired
    private FormDataMapper formDataMapper;

    @Autowired
    private MailConfigMapper mailConfigMapper;

    @Autowired
    private MailMapper mailMapper;


    /**
     * 根据名称查询报名表
     */
    @Override
    public FormInfo findFormInfoByFormName(String formName) {

        if (StringUtils.isEmpty(formName) || "".equals(formName)) {
            throw new IllegalArgumentException("表单名错误");
        }
        FormInfo formInfo = formDataMapper.findFormInfoByFormName(formName);
        if (null == formInfo) {
            throw new RuntimeException("找不到这个表单,请检查表单名是否正确。");
        }
        return formInfo;
    }

    /**
     * 插入表单数据
     */
    @Override
    public String insertDataByFormName(String formName, Map<String, Object> mapData) {
        if (null == mapData) {
            throw new RuntimeException("表单数据错误");
        }

        if (StringUtils.isEmpty(formName)) {
            throw new IllegalArgumentException("表单名错误");
        }

        // 根据表单名称查询是否符合时间范围
        FormInfo formInfo = formDataMapper.findFormInfoAllByFormName(formName);

        if (null == formInfo) {
            throw new RuntimeException("这个表单已经不存在");
        }
        Date formStartTime = formInfo.getFormStartTime();
        Date formStopTime = formInfo.getFormStopTime();

        if (System.currentTimeMillis() < formStartTime.getTime()) {
            return "活动还没开始";
        }
        if (System.currentTimeMillis() > formStopTime.getTime()) {
            return "活动已经结束";
        }

        //如果不可重复提交
        if (formInfo.getFormRepeat() == 0 ) {
            try{
                String phoneField = (String)mapData.get("phoneField");
                String repeatPhone = (String)mapData.get("repeatPhone");
                if(StringUtils.isNotEmpty(phoneField) && StringUtils.isNotEmpty(repeatPhone)){
                    int count = formDataMapper.findRepeatPhoneByFormName(formName,mapData.get("phoneField"),mapData.get("repeatPhone"));
                    if(count >0 ){
                        return "您的号码已经存在，请勿重复提交！";
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        String ipAddr = IpUtils.getIpAddr();


        try {
            //插入数据之前 ，移除判断重复的2个字段
            mapData.remove("repeatPhone");
            mapData.remove("phoneField");
            formDataMapper.insertDataByFormName(formName, mapData, ipAddr);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("提交失败 o(；>△<)o");
        }

        // 如果邮箱不为空 获取需要的数据来编辑html表格 作为邮箱内容
        Mail mail = new Mail();
        mail.setFormId(formInfo.getId());
        List<Mail> mailList = mailMapper.selectAllocatedListByForm(mail);

        if (mailList.size() > 0) {
            String formField = formInfo.getFormField();
            String formTitle = formInfo.getFormTitle();
            String[] split = formField.split(",");
            int i = 0;
            StringBuilder sb = new StringBuilder();
            sb.append("<table border=\"1\"  cellpadding=\"10\">");
            for (Map.Entry<String, Object> entry : mapData.entrySet()) {
                String tr = "<tr><td><strong>" + split[i] + "</strong></td><td>"
                        + "<div style=\"width: 500px;white-space: nowrap; text-overflow: ellipsis;overflow: hidden; \" title=\""
                        + entry.getValue() + "\">" + entry.getValue() + "</div></td></tr>";
                sb.append(tr);
                i++;
            }
            sb.append("<tr><td><strong>提交时间</strong></td><td>"
                    + new Date(System.currentTimeMillis()).toLocaleString() + "</td></tr>");
            sb.append("<tr><td><strong>ip</strong></td><td>" + ipAddr + "</td></tr>");
            sb.append("</table>");
            String html = sb.toString();

            try {
                // 获取发件人邮箱服务器地址、端口、邮箱、授权码
                MailConfig mailConfig = mailConfigMapper.selectMailConfigById();
                MailUtil.sendMail(mailConfig, mailList, html, formTitle);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (!StringUtils.isEmpty(formInfo.getFormSuccess())) {
            return formInfo.getFormSuccess();
        } else {
            return "提交成功！";
        }
    }

    @Override
    public String sendVerify(String phone, String type) {
        if (StringUtils.isEmpty(phone)) {
            throw new RuntimeException("手机号码为空");
        }
        if (StringUtils.isEmpty(type)) {
            throw new RuntimeException("出现了异常，请稍后再试。");
        }

        String ip = IpUtils.getIpAddr();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 一小时前的时间
        long time = System.currentTimeMillis() - 3600 * 1000;
        String formatTime = simpleDateFormat.format(time);

        // 一小时内此IP发送的次数
        int count = formDataMapper.selectVerifyTimeByIP(ip, formatTime);

        try {
            String phoneLastTime = formDataMapper.selectVerifyTimeByPhone(phone);
            if (phoneLastTime != null) {
                long phoneTime = simpleDateFormat.parse(phoneLastTime.split("\\.")[0]).getTime();
                if (Math.abs(new Date(System.currentTimeMillis() + 1000 * 3600 * 8).getTime() - phoneTime) < 50000) {
                    throw new RuntimeException("发送过于频繁，请稍后再试！");
                }
            }
        } catch (Exception e1) {
            throw new RuntimeException("发送过于频繁，请稍后再试！");
        }
        if (count >= 10) {
            throw new RuntimeException("近期发送过于频繁，请稍后再试！");
        }
        try {
            // 发送短信
            String verifyNumber = NoteUtil.sendNote(phone, type);
            // 保存本次发送的手机和IP
            formDataMapper.saveNoteVerify(phone, ip, verifyNumber);

            return "发送成功";
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("发送失败");
        }
    }

    @Override
    public String verifyNum(String phone, String verifyNum) {
        if (StringUtils.isEmpty(phone)) {
            throw new RuntimeException("手机号码为空");
        }
        if (StringUtils.isEmpty(verifyNum)) {
            throw new RuntimeException("验证码为空");
        }
        try {
            String selectVerifyNum = formDataMapper.verifyNum(phone);
            if (selectVerifyNum == null || !selectVerifyNum.equals(verifyNum)) {
                throw new RuntimeException("验证失败");
            }
        } catch (Exception e) {
            throw new RuntimeException("验证失败");
        }
        return "验证成功";
    }



}
