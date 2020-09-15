package com.ek.project.system.mailConfig.domain;

import com.ek.framework.aspectj.lang.annotation.Excel;
import com.ek.framework.web.damain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * 邮箱配置对象 mail_config
 * 
 * @author eric
 * @date 2020-09-08
 */
public class MailConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** stmp地址 */
    @Excel(name = "stmp地址")
    private String smtpAdress;

    /** stmp端口 */
    @Excel(name = "stmp端口")
    private String smtpPort;

    /** stmp邮箱 */
    @Excel(name = "stmp邮箱")
    private String sendMail;

    /** 授权码 */
    @Excel(name = "授权码")
    private String authCode;

    /** 发送人名称 */
    @Excel(name = "发送人名称")
    private String sendName;

    /** 发送模式 */
    @Excel(name = "发送模式")
    private String sendStyle;

    public void setSmtpAdress(String smtpAdress) 
    {
        this.smtpAdress = smtpAdress;
    }

    public String getSmtpAdress() 
    {
        return smtpAdress;
    }
    public void setSmtpPort(String smtpPort) 
    {
        this.smtpPort = smtpPort;
    }

    public String getSmtpPort() 
    {
        return smtpPort;
    }
    public void setSendMail(String sendMail) 
    {
        this.sendMail = sendMail;
    }

    public String getSendMail() 
    {
        return sendMail;
    }
    public void setAuthCode(String authCode) 
    {
        this.authCode = authCode;
    }

    public String getAuthCode() 
    {
        return authCode;
    }
    public void setSendName(String sendName) 
    {
        this.sendName = sendName;
    }

    public String getSendName() 
    {
        return sendName;
    }
    public void setSendStyle(String sendStyle) 
    {
        this.sendStyle = sendStyle;
    }

    public String getSendStyle() 
    {
        return sendStyle;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("smtpAdress", getSmtpAdress())
            .append("smtpPort", getSmtpPort())
            .append("sendMail", getSendMail())
            .append("authCode", getAuthCode())
            .append("sendName", getSendName())
            .append("sendStyle", getSendStyle())
            .toString();
    }
}
