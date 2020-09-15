package com.ek.project.system.form.service.impl;

import com.ek.common.utils.IpUtils;
import com.ek.common.utils.MailUtil;
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

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 报名表Service业务层处理
 * 
 * @author eric
 * @date 2020-08-28
 */
@Service
public class FormDataServiceImpl implements IFormDataService
{
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

        if (System.currentTimeMillis() < formStartTime.getTime() ) {
            return"活动还没开始";
        }
        if (System.currentTimeMillis() > formStopTime.getTime() ) {
            return"活动已经结束";
        }

        String ipAddr = IpUtils.getIpAddr();


        try {
            formDataMapper.insertDataByFormName(formName, mapData, ipAddr);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("提交失败 o(；>△<)o");
        }

        // 如果邮箱不为空 获取需要的数据来编辑html表格 作为邮箱内容
        Mail mail =  new Mail();
        mail.setFormId(formInfo.getId());
        List<Mail> mailList = mailMapper.selectAllocatedListByForm(mail);

        if (mailList.size()>0) {
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


}
