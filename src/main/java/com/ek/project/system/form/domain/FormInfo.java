package com.ek.project.system.form.domain;

import com.ek.framework.aspectj.lang.annotation.Excel;
import com.ek.framework.web.damain.BaseEntity;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 报名表对象 form_info
 * 
 * @author eric
 * @date 2020-08-28
 */
public class FormInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 表单标题 */
    @Excel(name = "表单标题")
    private String formTitle;

    /** 表单名称 */
    private String formName;

    /** 表单字段 */
    private String formField;

    /** 表单报名开始时间 */
    @Excel(name = "表单报名开始时间", width = 30, dateFormat = "yyyy-MM-dd hh:mm:ss")
    private Date formStartTime;

    /** 表单报名结束时间 */
    @Excel(name = "表单报名结束时间", width = 30, dateFormat = "yyyy-MM-dd hh:mm:ss")
    private Date formStopTime;

    /** 表单结构 */
    private String formHtml;



    /** 表单主题json */
    private String formJson;



    /** 表单css */
    private String formCss;

    /** 表单是否引用bootstarp */
    private Integer formStrap;

    /** 提交成功后提示语 */
    private String formSuccess;

    /** 表单提交文字 */
    private String formButton;

    /** 表单是否启用验证码 */
    private Integer formNote;

    /** 表单验证码类型 */
    private Integer formNoteType;

    /** 表单提交后跳转链接 */
    private String formHref;

    /** 表单是否可重复提交 */
    private Integer formRepeat;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setFormTitle(String formTitle) 
    {
        this.formTitle = formTitle;
    }

    @NotBlank(message = "表单标题不能为空")
    public String getFormTitle() 
    {
        return formTitle;
    }

    public void setFormName(String formName) 
    {
        this.formName = formName;
    }

    public String getFormName() 
    {
        return formName;
    }

    public void setFormField(String formField) 
    {
        this.formField = formField;
    }

    public String getFormField() 
    {
        return formField;
    }

    public void setFormStartTime(Date formStartTime) 
    {
        this.formStartTime = formStartTime;
    }


    public Date getFormStartTime() 
    {
        return formStartTime;
    }

    public void setFormStopTime(Date formStopTime) 
    {
        this.formStopTime = formStopTime;
    }

    public Date getFormStopTime() 
    {
        return formStopTime;
    }

    public void setFormHtml(String formHtml) 
    {
        this.formHtml = formHtml;
    }

    public String getFormHtml() 
    {
        return formHtml;
    }


    public void setFormJson(String formJson) 
    {
        this.formJson = formJson;
    }

    public String getFormJson() 
    {
        return formJson;
    }

    public void setFormCss(String formCss) 
    {
        this.formCss = formCss;
    }

    public String getFormCss() 
    {
        return formCss;
    }
    public void setFormStrap(Integer formStrap) 
    {
        this.formStrap = formStrap;
    }

    public Integer getFormStrap() 
    {
        return formStrap;
    }
    public void setFormSuccess(String formSuccess) 
    {
        this.formSuccess = formSuccess;
    }

    public String getFormSuccess() 
    {
        return formSuccess;
    }
    public void setFormNote(Integer formNote) 
    {
        this.formNote = formNote;
    }

    public Integer getFormNote() 
    {
        return formNote;
    }

    public Integer getFormNoteType() {
        return formNoteType;
    }

    public void setFormNoteType(Integer formNoteType) {
        this.formNoteType = formNoteType;
    }

    public String getFormButton() {
        return formButton;
    }

    public void setFormButton(String formButton) {
        this.formButton = formButton;
    }

    public String getFormHref() {
        return formHref;
    }

    public void setFormHref(String formHref) {
        this.formHref = formHref;
    }

    public Integer getFormRepeat() {
        return formRepeat;
    }

    public void setFormRepeat(Integer formRepeat) {
        this.formRepeat = formRepeat;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FormInfo{");
        sb.append("id=").append(id);
        sb.append(", formTitle='").append(formTitle).append('\'');
        sb.append(", formName='").append(formName).append('\'');
        sb.append(", formField='").append(formField).append('\'');
        sb.append(", formStartTime=").append(formStartTime);
        sb.append(", formStopTime=").append(formStopTime);
        sb.append(", formHtml='").append(formHtml).append('\'');
        sb.append(", formJson='").append(formJson).append('\'');
        sb.append(", formCss='").append(formCss).append('\'');
        sb.append(", formStrap=").append(formStrap);
        sb.append(", formSuccess='").append(formSuccess).append('\'');
        sb.append(", formButton='").append(formButton).append('\'');
        sb.append(", formNote=").append(formNote);
        sb.append(", formNoteType=").append(formNoteType);
        sb.append(", formHref='").append(formHref).append('\'');
        sb.append(", formRepeat=").append(formRepeat);
        sb.append('}');
        return sb.toString();
    }
}
