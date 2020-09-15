package com.ek.project.system.form.service;

import com.ek.project.system.form.domain.FormInfo;

import java.util.Map;

/**
 * 报名表Service接口
 * 
 * @author eric
 * @date 2020-08-28
 */
public interface IFormDataService
{

    /**
     * 根据名称查询报名表
     */
    public FormInfo findFormInfoByFormName(String formName);
    /**
     * 插入表单数据
     */
    public String insertDataByFormName(String formName, Map<String, Object> map);
}
