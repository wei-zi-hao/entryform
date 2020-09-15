package com.ek.project.system.mail.domain;

import com.ek.framework.aspectj.lang.annotation.Excel;
import com.ek.framework.web.damain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 邮箱对象 mail
 * 
 * @author eric
 * @date 2020-09-03
 */
public class Mail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 邮箱ID */
    @Excel(name = "邮箱ID")
    private Long mailId;

    /** 邮箱账号 */
    @Excel(name = "邮箱账号")
    private String mailAccount;

    /** 表单Id */
    private Long formId;

    public void setMailId(Long mailId) 
    {
        this.mailId = mailId;
    }

    public Long getMailId() 
    {
        return mailId;
    }
    public void setMailAccount(String mailAccount) 
    {
        this.mailAccount = mailAccount;
    }

    public String getMailAccount() 
    {
        return mailAccount;
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
            .append("mailAccount", getMailAccount())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
