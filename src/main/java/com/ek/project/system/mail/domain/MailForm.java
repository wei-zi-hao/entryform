package com.ek.project.system.mail.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 用户和角色关联 sys_user_role
 * 
 * @author ruoyi
 */
public class MailForm
{
    /** 邮箱ID */
    private Long mailId;
    
    /** 表单ID */
    private Long formId;

    public Long getMailId() {
        return mailId;
    }

    public void setMailId(Long mailId) {
        this.mailId = mailId;
    }

    public Long getFormId() {
        return formId;
    }

    public void setFormId(Long formId) {
        this.formId = formId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("mailId", getMailId())
            .append("formId", getFormId())
            .toString();
    }
}
