package com.ek.project.system.form.mapper;

import com.ek.project.system.form.domain.FormInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 报名表Mapper接口
 * 
 * @author eric
 * @date 2020-08-28
 */
public interface FormDataMapper
{

    /**
     * 通过名称查询表单部分数据
     * */
    public FormInfo findFormInfoByFormName(String formName);

    /**
     * 插入表单数据
     * */
    public void insertDataByFormName(@Param("formName")String formName, @Param("mapData")Map<String, Object> mapData, @Param("ipAddr")String ipAddr);

    /**
     * 通过名称查询表单全部数据
     * */
    public FormInfo findFormInfoAllByFormName(String formName);
}
